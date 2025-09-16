# Factory Design Pattern Notes
the Factory Pattern is a creational design pattern that abstracts the process of object creation.

## Simple Factory - Basic way

Let's say there is a parent class Bird and we have different types of birds like Hen, Pigeon etc.

```java
class Factory {
    static Bird getBird(String type) {
        if(type.equals("Hen")) {
            return new Hen();
        } else if(type.equals("Pigeon")) {
            return new Pigeon();
        }
    }
}
```

**Problem:** What if the Bird type is not given? How can we implement this to return the correct getBird()?

The above code is called **Simple Factory**.

---

## Simple Factory for Stone Example

Before we had Factory Method, let's see how Simple Factory would work for stones:

```java
class StoneFactory {
    static Stone getStone(String level, String type) {
        if(level.equals("Level-1")) {
            // For balanced version - we need equal distribution
            // But how do we track what was created before?
            if(type.equals("small")) return new SmallStone();
            if(type.equals("medium")) return new MediumStone();
            if(type.equals("large")) return new LargeStone();
        } else if(level.equals("Level-2")) {
            // For random version
            Random rand = new Random();
            int choice = rand.nextInt(3);
            if(choice == 0) return new SmallStone();
            if(choice == 1) return new MediumStone();
            return new LargeStone();
        }
    }
}
```

### Problems with Simple Factory for Stones:
2. **Complex Logic**: The factory becomes complex with multiple if-else conditions
3. **Hard to Extend**: Adding new levels means modifying the existing factory
4. **Mixed Responsibilities**: One class handling multiple creation strategies

---

## Factory Method - Better approach

**Game scenario:** Let's say we have two games, before we start the game we get the option to pick level-1 or level-2
- **Level-1:** Equal distribution of all three stones → BalancedVersion  
- **Level-2:** Stones are generated randomly → small, big, medium

**Now, how do we generate these stones?**
→ Two strategies to generate stones!

### How we solve this:

Earlier we had only one StoneFactory, by taking the input we were generating accordingly.

This time, there are two different types of factories that exist. We can have an abstraction:
- StoneFactory has a getStone()
- BalancedVersion implements getStone()  
- RandomVersion implements getStone()

### In client code:
```java
void render(IStoneFactory sf) {
    sf.getStone();
    // This will call the respective stone function through polymorphism
}
```

### BalancedVersion implementation:
- Uses Queue - LinkedList
- Pop from front and push from back to generate balanced version of stones
- If we got small, medium, next getStone should send large stone
- After every three stones, the count of stones should be same
- The behaviour has to be predicted

### RandomVersion implementation:
- Generates the objects of small or large or big stone randomly
- If we select random one, we can't predict which is generated

### Why this is better:
- Interface helps us create a layer of abstraction 
- Client need not know what all types exist
- They just call the .getStone() of the respective object they want
- Does not take any value → calls the getStone written in our factory
- We have different factory classes to create objects of different classes

**This is known as Factory Method Design Pattern** - where we have an abstract factory and concrete factories such as balanced and random.

---

## How to Explain Factory Design Pattern (Viva Format)

### Definition
"Factory is a creational design pattern which helps us to abstract the object creation logic at one place, in one class which is called factory."

### Bird Example (Simple Factory)
- **Problem**: Instead of creating objects directly using `new Hen()`, `new Pigeon()` everywhere
- **Solution**: Use factory that takes input and creates objects accordingly
- **Example**: `BirdFactory.getBird("Hen")` → creates Hen object
- **Key Point**: All created birds can still call their methods like `fly()`, `eat()` because they inherit from Bird class

### Stone Example (Factory Method)
- **Problem**: What if we need different creation strategies?
- **Solution**: Multiple factories implementing same interface `IStoneFactory`
- **BalancedStoneFactory**: Creates stones in equal distribution (Level-1)
- **RandomStoneFactory**: Creates stones randomly (Level-2)
- **Key Point**: Both call `getStone()` method but with different creation logic

### Core Understanding
- **Simple Factory**: One factory takes input, creates appropriate objects
- **Factory Method**: Multiple factories, each with different creation strategies
- **Benefit**: Centralized creation logic, easy maintenance, supports polymorphism
- **Real Usage**: Client calls factory methods without knowing internal creation details