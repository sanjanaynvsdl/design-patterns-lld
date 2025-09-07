# Prototype Design Pattern

## Problem
- Heavy constructors consume lots of time (DB connections, loading attributes)
- Game example: Thousands of characters with same attributes but different values
- Creating each character = expensive DB query
- Need to avoid repeated constructor calls

## Solution
- Create prototype objects once
- Clone them at runtime instead of using constructors
- Modify cloned objects as needed

## How it works
1. Create base prototypes (5 unique character types)
2. Store them in registry
3. When needed: get clone + modify properties
4. Avoid heavy constructor calls

## Implementation
- Add `clone()` method to classes
- Use Cloneable interface to enforce contract
- Registry pattern to store and retrieve prototypes

```java
public class A implements Cloneable {
    private String name;
    private char symbol;

    @Override
    public A getCopyObj() {
        A copy = new A();
        copy.name = this.name;
        copy.symbol = this.symbol;
        return copy;
    }
}

interface Cloneable {
    A getCopyObj();
}

// This pattern helps us optimize object creation
```

## Example Implementation
- Item abstract class
- Movie, Book extends this
- Now we need different movies and different books, so we create copies of books and movies
- So we have a cloneable interface
- Before creation of objects, registry's load method should be called
- What does this load do?
- This creates all prototypes present in the system
- Different movies, different books - they are created in load function and loaded in the HashMap 
