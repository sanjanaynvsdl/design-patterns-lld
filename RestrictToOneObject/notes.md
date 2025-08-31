# Singleton Design Pattern - Implementation Steps

## Goal
Design a class where only ONE single object can be created throughout the application.

## Real-World Use Cases
1. **Database Connection** - Expensive to create, reuse single connection pool
2. **Logger** - Central logging mechanism for entire application  
3. **Configuration Manager** - Single source of app settings

## Problem Statement
How can we restrict a class to create only one object?
- Normally, we use constructors to create objects
- But constructors allow unlimited object creation
- We need a way to control and limit object creation

---

## Step 1: Eager Loading (Simple but Memory Intensive)

### Implementation
- Make constructor **private** → Prevents external object creation
- Create a **static** instance as class attribute → Created when class loads
- Provide **public static method** (getInstance) → Returns the single instance

### Code Structure
```
private static DBConnection instance = new DBConnection("sql");
private DBConnection(String type) { ... }
public static DBConnection getInstance() { return instance; }
```

### Pros & Cons
✅ **Pros:** Thread-safe by default, Simple implementation
❌ **Cons:** Memory wasted if never used (created at class loading time)

---

## Step 2: Lazy Loading (Create When Needed)

### Implementation  
- Don't create instance at class level
- Create instance inside getInstance() method
- Check if null, then create

### Code Structure
```
private static DBConnection instance = null;
public static DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection("mongo");
    }
    return instance;
}
```

### Pros & Cons
✅ **Pros:** Saves memory, creates only when needed
❌ **Cons:** NOT thread-safe! Multiple threads can create multiple objects

---

## Step 3: Synchronized Method (Thread-Safe but Slow)

### Problem with Step 2
- Thread 1 checks if null → true
- Thread 2 checks if null → also true (before T1 creates)
- Both create objects → Singleton violated!

### Implementation
- Make entire getInstance() method **synchronized**
- Only one thread can execute at a time

### Code Structure
```
public static synchronized DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection("mongo");
    }
    return instance;
}
```

### Pros & Cons
✅ **Pros:** Thread-safe, prevents multiple object creation
❌ **Cons:** VERY SLOW - threads wait even after object is created

---

## Step 4: Double-Checked Locking (Optimized)

### Why Double Check?
- First check: Avoid entering synchronized block if instance exists
- Second check: Ensure only one thread creates object inside synchronized block

### Implementation
```
public static DBConnection getInstance() {
    if (instance == null) {                      // First check (no lock)
        synchronized(DBConnection.class) {        // Lock only if null
            if (instance == null) {               // Second check (with lock)
                instance = new DBConnection();
            }
        }
    }
    return instance;
}
```

### How it Works
1. After instance is created, threads never enter synchronized block
2. Only during creation, threads synchronize
3. Much faster than Step 3!

---

## Step 5: Volatile Keyword (CPU Cache Fix)

### The Hidden Problem
- Each CPU core has its own cache
- Thread 1 creates object in its cache (not main memory yet)
- Thread 2 checks its own cache → sees null → creates another object!

### Solution: volatile
- Forces read/write from main memory
- Prevents CPU cache inconsistencies
- Guarantees visibility across all threads

### Final Implementation
```
private static volatile DBConnection instance;  // Note: volatile keyword

public static DBConnection getInstance() {
    if (instance == null) {
        synchronized(DBConnection.class) {
            if (instance == null) {
                instance = new DBConnection();
            }
        }
    }
    return instance;
}
```

---

## Decision Guide

**Use Eager Loading when:**
- Memory is not a constraint
- You're certain the singleton will be used
- Simplicity is priority

**Use Double-Checked Locking with Volatile when:**
- Memory is limited
- Singleton might not be used
- Performance is critical

---

## Step 6: Enum-Based Singleton - The Simplest Solution!

### Why Enum for Singleton?
Enum is the SIMPLEST and SAFEST way to implement singleton pattern. The JVM handles everything automatically!

### Implementation Structure
```java
public enum DBConnectionEnum {
    INSTANCE("sql");  // Only ONE instance, created when enum is loaded
    
    private String type;
    
    // Enum constructors are ALWAYS private (can't write public/protected)
    DBConnectionEnum(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    // No need for getInstance() method!
    // Just use: DBConnectionEnum.INSTANCE
}
```

### How It Works:
1. **INSTANCE** is the single enum constant - only ONE can exist
2. **JVM Guarantee:** Only one instance of each enum constant
3. **Thread-Safe:** Enum initialization is thread-safe by default
4. **No getInstance() needed:** Direct access via `DBConnectionEnum.INSTANCE`

### Usage:
```java
DBConnectionEnum db1 = DBConnectionEnum.INSTANCE;
DBConnectionEnum db2 = DBConnectionEnum.INSTANCE;
// db1 == db2 will always be true!

String type = DBConnectionEnum.INSTANCE.getType();
```

### Why This is SUPER SAFE:

✅ **Thread-Safe:** JVM handles synchronization automatically  
✅ **Reflection-Proof:** Can't create new enum instances via reflection
✅ **Serialization-Safe:** JVM ensures same instance after deserialization
✅ **Simple Code:** No complex logic needed
✅ **Memory Efficient:** Created only when first referenced

### When to Use Enum Singleton:
- When you want the SAFEST implementation
- When simplicity is most important  
- When you need protection against reflection attacks
- When you don't need lazy loading (enum loads when first referenced)

## Step 7: Inner Class Pattern (Bill Pugh Solution) - Best of Both Worlds!

### The Problem with Previous Solutions
- **Eager Loading:** Wastes memory (creates even if never used)
- **Lazy Loading:** Not thread-safe or requires expensive synchronization
- **Synchronized:** Slow performance (threads wait even after object exists)
- **Double-Checked Locking:** Complex code, requires volatile keyword

### Inner Class Solution - How It Works
The inner class singleton pattern leverages JVM's class loading mechanism to achieve both lazy loading AND thread safety automatically!

### Key Concepts:
1. **Static Inner Class:** Only loaded when first referenced
2. **JVM Class Loading:** Thread-safe by default (JVM handles synchronization)
3. **Lazy Initialization:** Object created only when getInstance() is called
4. **No Performance Penalty:** No synchronization needed after initialization

### Implementation Structure
```java
public class DBConnectionInnerClass {
    private String type;
    
    // Private constructor prevents external object creation
    private DBConnectionInnerClass(String type) {
        this.type = type;
    }
    
    // Public method to get instance
    public static DBConnectionInnerClass getInstance() {
        return SingleTonHelper.instance;  // Triggers inner class loading
    }
    
    public String getType() {
        return this.type;
    }
    
    // Static inner class - loaded only when referenced
    private static class SingleTonHelper {
        // Instance created when class is loaded (thread-safe by JVM)
        private static final DBConnectionInnerClass instance = 
            new DBConnectionInnerClass("sql");
    }
}
```

### Step-by-Step Process:

**What happens when getInstance() is called:**

1. **First Call:**
   - JVM loads `SingleTonHelper` class (lazy loading - only now!)
   - JVM initializes static field `instance` (thread-safe class initialization)
   - Creates `new DBConnectionInnerClass("sql")` exactly once
   - Returns the instance

2. **Subsequent Calls:**
   - `SingleTonHelper` class already loaded
   - Static field `instance` already exists
   - Just returns existing instance reference
   - No synchronization overhead!

### Why Inner Class is Private Static?

**`private`:** 
- Prevents external access to helper class
- Only our outer class should use it

**`static`:** 
- Can be accessed from static `getInstance()` method
- No reference to outer class instance (memory efficient)
- Loaded independently when first referenced (lazy loading)

### Thread Safety Deep Dive:
- **JVM Guarantee:** Class initialization happens exactly once per class loader
- **Automatic Synchronization:** JVM handles thread coordination during class loading
- **No Race Conditions:** Even if multiple threads call `getInstance()` simultaneously, JVM ensures single initialization
- **After Initialization:** No synchronization overhead, direct field access

### Why This is the BEST Solution:

✅ **Lazy Loading:** Object created only when needed (saves memory)
✅ **Thread-Safe:** JVM's class loading mechanism handles synchronization  
✅ **High Performance:** No synchronization after initialization
✅ **Simple Code:** Clean, readable implementation
✅ **No Volatile Needed:** Class loading guarantees visibility
✅ **No Double-Checking:** JVM handles the complexity

### Comparison Summary:
- **Eager:** Fast but wastes memory
- **Lazy:** Saves memory but not thread-safe
- **Synchronized:** Thread-safe but slow
- **Double-Checked:** Fast + safe but complex
- **Inner Class:** Fast + safe + simple + memory efficient! 🎯 