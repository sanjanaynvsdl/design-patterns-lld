# Factory Design Pattern - Class Diagram

## UML Diagram - Stone Game Factory Method Implementation

```
┌─────────────────────┐
│   Stone (Abstract)  │
├─────────────────────┤
│ # name: String      │
├─────────────────────┤
│ + Stone(String)     │
│ + getName(): String │
│   (abstract)        │
└─────────────────────┘
          △
          │ (extends)
    ┌─────┼─────┬─────┐
    │     │     │     │
┌───┴──┐ ┌┴────┐ ┌───┴──┐
│SmallSt│ │Medium│ │LargeSt│
│      │ │St   │ │      │
├──────┤ ├─────┤ ├──────┤
│+Small│ │+Medium│+Large│
│St(   │ │St(  │ │St(   │
│String│ │String)│String)│
│)     │ │+get  │ │+get  │
│+get  │ │Name()│ │Name()│
│Name()│ └─────┘ └──────┘
└──────┘


┌──────────────────────┐
│   <<interface>>      │
│   IStoneFactory      │
├──────────────────────┤
│ + getStone(): Stone  │
└──────────────────────┘
          △
          │ (implements)
    ┌─────┴─────┐
    │           │
┌───┴──────────────────┐  ┌────┴────────────────┐
│ BalancedVersion      │  │  RandomVersion      │
├──────────────────────┤  ├─────────────────────┤
│- stoneQueue: Queue   │  │+ getStone(): Stone  │
│  <String>            │  │  (random logic)     │
├──────────────────────┤  └─────────────────────┘
│+ getStone(): Stone   │
│  (balanced queue     │
│   logic)             │
└──────────────────────┘

         △
         │ (uses)
┌────────┴──────────┐
│      Main         │
│    (Client)       │
├───────────────────┤
│+ render(          │
│   IStoneFactory)  │
│+ main(String[])   │
└───────────────────┘
```

---

## Flow Diagram

### Balanced Version (6 calls):
```
Queue: [Small, Medium, Large]

Call 1: Small → [Medium, Large, Small]
Call 2: Medium → [Large, Small, Medium]
Call 3: Large → [Small, Medium, Large]
Call 4: Small → [Medium, Large, Small]
Call 5: Medium → [Large, Small, Medium]
Call 6: Large → [Small, Medium, Large]

Pattern repeats! Equal distribution ✓
```

### Random Version (6 calls):
```
Call 1: random(0-2) → e.g., Large
Call 2: random(0-2) → e.g., Small
Call 3: random(0-2) → e.g., Small
Call 4: random(0-2) → e.g., Medium
Call 5: random(0-2) → e.g., Large
Call 6: random(0-2) → e.g., Medium

Unpredictable!
```

---

## Simplified Structure

```
    IStoneFactory (interface)
           ↑
    ┌──────┴──────┐
    │             │
BalancedVersion  RandomVersion
    │             │
    └──────┬──────┘
           │ creates
           ▼
         Stone
           ↑
    ┌──────┼──────┐
    │      │      │
SmallSt MediumSt LargeSt
```

---

## Key Relationships

- **Stone** - Abstract parent class with name field
- **SmallSt, MediumSt, LargeSt** - Concrete stone types
- **IStoneFactory** - Factory interface with getStone() method
- **BalancedVersion** - Uses Queue for equal distribution
- **RandomVersion** - Uses Random for unpredictable generation
- **Main (Client)** - Uses factory through interface (polymorphism)

---

## How Client Uses It

```java
// Client doesn't know which factory
IStoneFactory sf = new BalancedVersion();
// or
IStoneFactory sf = new RandomVersion();

// Same code for both!
for(int i = 0; i < 6; i++) {
    render(sf);
}

static void render(IStoneFactory sf) {
    Stone stone = sf.getStone();
    System.out.println(stone.getName());
}
```

---

## Benefits

1. **Polymorphism** - Client uses IStoneFactory interface
2. **Easy to switch** - Change one line to switch factories
3. **Open/Closed** - Add new factory without modifying existing code
4. **Dependency Inversion** - Client depends on interface, not concrete classes
