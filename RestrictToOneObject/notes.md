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

## Step 6: Enum-Based Singleton (To Be Explored)
- Thread-safe by default
- Prevents reflection attacks
- Serialization handled automatically

## Step 7: Inner Class Pattern (Bill Pugh Solution) (To Be Explored)  
- Lazy loading without synchronization
- Uses class loading mechanism for thread safety
- Best of both worlds 