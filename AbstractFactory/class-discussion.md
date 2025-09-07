# Abstract Factory Design Pattern - Class Discussion

## Problem Statement
In the factory design pattern, we had small, medium and large stones. But what if we have stones from different planets (Mars, Jupiter, Earth) where each planet has its own small, medium, large stones with different colors and textures?

## Initial Approach (Simple Factory)
```java
class Factory {
    create smallStone;
    create mediumStone;
    create largeStone;
}
```

## Solution Discussed in Class

Since we have Mars, Jupiter, Earth stones (each with small, medium, large variations), we need different factories for each planet.

### Step 1: Create Abstract Factory Interface
```java
interface Factory {
    Stone getSmallStone();
    Stone getMediumStone();
    Stone getLargeStone();
}
```

### Step 2: Implement Concrete Factories
```java
class MarsFactory implements Factory {
    @Override
    Stone getSmallStone() {
        return new MarsSmallStone(); // Red colored, rough texture
    }
    
    @Override
    Stone getMediumStone() {
        return new MarsMediumStone();
    }
    
    @Override
    Stone getLargeStone() {
        return new MarsLargeStone();
    }
}

class EarthFactory implements Factory {
    @Override
    Stone getSmallStone() {
        return new EarthSmallStone(); // Brown/gray colored
    }
    
    @Override
    Stone getMediumStone() {
        return new EarthMediumStone();
    }
    
    @Override
    Stone getLargeStone() {
        return new EarthLargeStone();
    }
}
```

### Step 3: Stone Hierarchy
```java
interface Stone {
    // Common stone methods
}

class EarthStone implements Stone {
    // Earth-specific properties
}

class MarsStone implements Stone {
    // Mars-specific properties (different color, texture)
}
```

### Step 4: Client Code
```java
class Main {
    void render(Factory factory) {
        Stone stone = factory.getSmallStone();
        // Use the stone based on which factory was passed
    }
    
    public static void main(String[] args) {
        Factory factory = new EarthFactory();
        render(factory); // Will create Earth stones
        
        Factory marsFactory = new MarsFactory();
        render(marsFactory); // Will create Mars stones
    }
}
```
- one factory is responsible for creating differnt types of objects, 
- in previous factory design pattern we created only, Stone
- in this eexample, we have earthFactory, marsFactory, etc with all diff stones
