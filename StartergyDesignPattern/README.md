# Strategy Design Pattern

## Stage 1: Initial Requirements
**Design a list class that:**
- Supports only integer data-type
- Provides certain functions:
```java
reverse()
sort()
setIndex(i, int val)
print()
get(i)
add(num)

// Multiple clients will use this List class
// Client C1 wants to print the list vertically
// Client C2 wants to print horizontally
```

**Initial Solution (Using Inheritance):**
- Create a List class with the above methods
- Make the print method abstract
- Child classes like HorizontalList and VerticalList extend and override the print method

> **Note:** Always think of the current requirement first, instead of jumping to design patterns

---

## Stage 2: New Requirements
**Additional scenarios:**
- Client C1: has list of binary values (1,0)
- Client C2: has char values from ASCII
- Client C3: has numbers sorted in descending order

This means we need different behaviors not just for printing, but also for sorting.

### The Problem with Inheritance
- In scenario 1, we only had two types of printing behaviors
- Now with multiple sorting behaviors, creating classes for each combination leads to **class explosion** or messy hierarchy
- Whenever we see segregation of classes based on behaviors (and there are multiple behaviors), we need to stop and redesign

**Inheritance is not the answer when:**
- Classes are segregated based on behaviors
- There are multiple varying behaviors
- We end up with combinations like: HorizontalMergeSort, VerticalQuickSort, etc.

---

## The Solution: Composition Over Inheritance

Instead of creating multiple classes, we need:
- **One List class** where we can print as we want
- **One List class** where we can sort as we want
- Achieve this using **composition** (having objects/dependencies inside the class)

### How to Structure This:

**Inside the List class, we have:**
- SortingAlgorithm object
- PrintingAlgorithm object
- Delegate the actual work to these objects

```java
// In the print function, delegate to the printing object
printingAlgo.print();

// Clients can select sorting/printing strategies and inject via constructor
```

### Implementation Structure:

**Create Abstractions (Interfaces):**
```java
ISortingStrategy -> sort() method
IPrintStrategy -> print() method

// Implementations of these interfaces:
// MergeSort, TimSort, CountSort implement ISortingStrategy
// VerticalPrint, HorizontalPrint implement IPrintStrategy
```

**List Class with Composition:**
```java
ListClass {
    ISortingStrategy sortingAlgo;
    IPrintStrategy printStrategy;

    public ListClass(ISortingStrategy sortingStr, IPrintStrategy printStr) {
        this.sortingAlgo = sortingStr;
        this.printStrategy = printStr;
    }

    void print() {
        printStrategy.print();
    }

    void sort() {
        sortingAlgo.sort();
    }
}
```

**Future Extension:**
- If we want to add another behavior (like reverse)
- Create IReverseStrategy -> AscendingReverse, RandomReverse
- Add overloaded constructors to maintain backward compatibility

---

## This is the Strategy Design Pattern!

### Key Concepts:

**Inheritance vs Composition:**
- **Inheritance:** "IS-A" relationship (VerticalList IS-A ListCls)
- **Composition:** "HAS-A" relationship (List HAS-A PrintingStrategy, HAS-A SortingStrategy)

**Why Composition is Better:**
1. **Flexibility:** Change behavior at runtime
2. **No class explosion:** Instead of n×m classes for n print and m sort combinations, you have 1 List + n print strategies + m sort strategies
3. **Open/Closed Principle:** Add new strategies without modifying existing code
4. **Reusability:** Same strategy can be used by multiple clients

---

## Another Example: Bird Classes

**Problem:**
- Bird parent class -> Kiwi, Eagle, Pigeon
- Each has different eating, hunting, flying behaviors

**Variations:**
- Pigeon and Parrot: same flying, different hunting
- Pigeon and Eagle: same hunting, different flying

**Solution with Strategy Pattern:**
- Remove all inheritance, keep single Bird class
- Pass flying strategy at runtime via constructor
- Similarly for IHunting, IEating behaviors

### Implementation Benefits:

1. **Clean separation:** No Bird subclasses (Eagle, Pigeon, etc.) - just one Bird class with strategies
2. **Proper delegation:** `bird.fly()` delegates to `flyStrategy.fly()`
3. **Flexible composition:** Any bird can have any combination of flying and hunting behaviors
4. **Simple interfaces:** IFlyStrategy and IHuntStrategy with single methods

### Benefits Demonstrated:

- **No inheritance mess:** Instead of Eagle, Pigeon, Kiwi classes with overlapping behaviors
- **Mix and match:**
  ```java
  Eagle = new Bird(flyHigh, aerialHunt);
  Kiwi = new Bird(noFly, groundHunt);
  Pigeon = new Bird(flyLow, groundHunt);
  ```
- **Runtime flexibility:** Could even change strategies with setters if needed

---

## When to Use Strategy Pattern

**Warning Signs:**
- Questions often frame scenarios with only one behavior initially (leading to inheritance)
- Later, additional behaviors are introduced
- When you see methods with different behaviors across child classes

**Action:**
- Revert the inheritance approach
- Use Strategy Pattern with composition
- Inject behaviors via constructor or setters