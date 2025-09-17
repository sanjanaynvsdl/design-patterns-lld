# Strategy Design Pattern - Bird Example

## Implementation Overview
This example demonstrates the Strategy Design Pattern with bird behaviors.

## Key Concepts
- Behaviors like Flying, Hunting, Eating, Walking are **not directly implemented** by birds
- Instead, we use strategy classes like `FlyHigh`, `FlyLow`, `AerialHunt`, `GroundHunt`

## Implementation Approach
We use a single `Bird` class with strategy composition:
- Bird class has a `name` attribute passed through constructor
- Flying and hunting behaviors are injected as strategy objects
- This allows runtime behavior changes

## Code Example (from Main.java)
```java
// Creating birds with different strategies
Bird eagle = new Bird("Eagle", new FlyHigh(), new AerialHunt());
Bird pigeon = new Bird("Pigeon", new FlyLow(), new GroundHunt());

// Using behaviors
eagle.fly();   // Eagle: Flying high in the sky!
eagle.hunt();  // Eagle: Hunts prey while flying.

pigeon.fly();  // Pigeon: Flying close to the ground!
pigeon.hunt(); // Pigeon: Hunts small insects on the ground.

// Runtime behavior change
eagle.setFlyStrategy(new FlyLow());
eagle.fly();  // Eagle: Flying close to the ground!
```

## Benefits
- **Flexibility**: Can change bird behavior at runtime
- **Composition over Inheritance**: No need for concrete bird subclasses
- **Open/Closed Principle**: Easy to add new behaviors without modifying existing code

## Notes
- Alternative approach could use concrete classes (Eagle, Pigeon extends Bird)
- Current approach with strategy injection is more flexible