# Immutable Objects & Builder Pattern

## Immutable Objects
- Objects that cannot be changed after creation
- Values remain constant throughout object lifetime

## Rules for Immutable Class
1. **Attributes**: private, final
2. **No setters** allowed
3. **Getters**: return deep copy for mutable objects
4. **Class**: should be final (no inheritance)

## Problem with Constructor
- Need to pass ALL attributes (20-30 parameters)
- Not flexible for clients who want only few values
- Like pizza order - some want cheese, some don't

## Solutions Tried
HashMap from user - client still needs to know attribute names
JSON object - client still needs to know keys  
Problem: If we change attribute names, client code breaks


### Final Usage
```java
Temp temp = new Temp.Builder()
    .setName("sanjana")
    .setEmail("sanju@gmail.com")
    .build(); // returns Temp object
```

