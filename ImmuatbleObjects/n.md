# Immutable Objects & Builder Pattern

## Immutable Objects

### Definition
- Objects that cannot be changed after creation
- Values remain constant throughout object lifetime
- Ensures thread safety and prevents accidental modifications

### Rules for Creating Immutable Classes
1. **Attributes**: Declare all fields as private and final
2. **No setters**: Don't provide setter methods
3. **Getters**: Return deep copies for mutable objects (like arrays, collections)
4. **Class**: Make the class final to prevent inheritance
5. **Constructor**: Initialize all fields through constructor only

### Benefits
- Thread-safe by default
- Easier to cache and share between threads
- Prevents defensive copying
- Simplifies debugging and testing

---

## Builder Pattern

### Definition
Builder is a **creational design pattern** that helps construct complex objects step by step. It allows you to create different representations of an object using the same construction process.

### Problem Solved
- **Complex constructors**: Avoids constructors with many parameters (20-30 parameters)
- **Flexibility**: Clients can specify only the values they need
- **Readability**: Method chaining makes code more readable
- **Optional parameters**: Handle objects with many optional fields elegantly

### Why Not Other Solutions?

#### HashMap Approach
```java
// Problem: Client needs to know exact attribute names
Map<String, Object> params = new HashMap<>();
params.put("name", "sanjana");
params.put("email", "sanju@gmail.com");
```

#### JSON Object Approach
```java
// Problem: Client still needs to know keys
// If attribute names change, client code breaks
```

### Builder Pattern Implementation

#### Usage Example
```java
Temp temp = new Temp.Builder()
    .setName("sanjana")
    .setEmail("sanju@gmail.com")
    .build(); // returns immutable Temp object
```

### When to Use Builder Pattern
- Object has many parameters (especially optional ones)
- Object creation is complex
- You want to create immutable objects
- You need different representations of the same object

### Benefits
- **Readable code**: Method chaining improves readability
- **Flexible construction**: Set only required parameters
- **Immutable objects**: Perfect for creating immutable instances
- **Validation**: Can validate object state before creation

