# Factory Design Pattern Notes

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