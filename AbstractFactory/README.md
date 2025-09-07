# Abstract Factory Design Pattern

## What is Abstract Factory Design Pattern?
Abstract Factory is a creational design pattern that provides an interface for creating **families of related objects** without specifying their concrete classes.

## Key Difference from Factory Method
- **Factory Method**: Creates objects of one type (like stones: small, medium, large)
- **Abstract Factory**: Creates families of related objects (like entire UI themes, game environments, etc.)

## Why Do We Need Abstract Factory?

### Problem Scenario
Let's extend your game example. Now your game needs to support different themes:

**Winter Theme:**
- WinterStone (small, medium, large)
- WinterBackground
- WinterSound effects

**Summer Theme:**
- SummerStone (small, medium, large)
- SummerBackground
- SummerSound effects

### Without Abstract Factory (Problems):
Client code becomes messy:
```java
if (theme.equals("Winter")) {
    Stone stone = new WinterStoneFactory().getStone();
    Background bg = new WinterBackgroundFactory().getBackground();
    Sound sound = new WinterSoundFactory().getSound();
} else if (theme.equals("Summer")) {
    Stone stone = new SummerStoneFactory().getStone();
    Background bg = new SummerBackgroundFactory().getBackground();
    Sound sound = new SummerSoundFactory().getSound();
}
```

### With Abstract Factory (Solution):
```java
// 1. Abstract Factory Interface
interface GameFactory {
    IStoneFactory getStoneFactory();
    Background getBackground();
    Sound getSound();
}

// 2. Concrete Factories
class WinterGameFactory implements GameFactory {
    public IStoneFactory getStoneFactory() { return new WinterStoneFactory(); }
    public Background getBackground() { return new WinterBackground(); }
    public Sound getSound() { return new WinterSound(); }
}

// 3. Clean Client Code
void initializeGame(GameFactory factory) {
     IStoneFactory stoneFactory = gameFactory.getStoneFactory();
          Background background = gameFactory.getBackground();       
          Character character = gameFactory.getCharacter();

          // Use the objects
          background.render();
          character.move();

          // Get stones using the stone factory (like your current render method)
          Stone stone = stoneFactory.getStone();
          System.out.println("Stone: " + stone.getName());
          System.out.println("Background: " + background.getType()); 
          System.out.println("Character: " + character.getName());  
}

// Client usage (like your Main.java format)
public static void main(String[] args) {
    GameFactory gameFactory = new WinterGameFactory(); // or SummerGameFactory
    
    // Call client method - similar to your render(sf) call
    initializeGame(gameFactory);
    
    // You can also loop like your current code
    for (int i = 0; i < 3; i++) {
        initializeGame(gameFactory);
    }
}
```

## When to Use:
- When you need families of related/dependent objects
- When you want to ensure objects from same family work together
- When you need multiple product lines/themes/platforms

## Key Concept:
Abstract Factory = Factory Method + One More Layer (creates families instead of single objects)

-------------------------------------

In factory we creaed only objects of stone class
- But in the abstract factory, we can create the multiple types of objects, 