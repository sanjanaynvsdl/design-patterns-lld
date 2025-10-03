# Abstract Factory Design Pattern - Class Diagram

## UML Diagram - Game Theme Factory Implementation

```
┌─────────────────────────────────────┐
│       <<interface>>                 │
│       GameFactory                   │
│    (ABSTRACT FACTORY)               │
├─────────────────────────────────────┤
│ + getStoneFactory(): IStoneFactory  │
│ + getBackground(): Background       │
│ + getCharacter(): Character         │
└─────────────────────────────────────┘
                △
                │ (implements)
         ┌──────┴──────┐
         │             │
┌────────┴─────────────────┐  ┌──────┴────────────────────┐
│  WinterGameFactory       │  │  SummerGameFactory        │
├──────────────────────────┤  ├───────────────────────────┤
│ + getStoneFactory():     │  │ + getStoneFactory():      │
│     IStoneFactory        │  │     IStoneFactory         │
│   → WinterStoneFactory   │  │   → SummerStoneFactory    │
│                          │  │                           │
│ + getBackground():       │  │ + getBackground():        │
│     Background           │  │     Background            │
│   → WinterBackground     │  │   → SummerBackground      │
│                          │  │                           │
│ + getCharacter():        │  │ + getCharacter():         │
│     Character            │  │     Character             │
│   → WinterCharacter      │  │   → SummerCharacter       │
└──────────────────────────┘  └───────────────────────────┘
         │                              │
         │ creates                      │ creates
         ▼                              ▼
    WINTER FAMILY                  SUMMER FAMILY
    ═════════════                  ═════════════
```

---

## Product Families

### Stone Factory
```
┌──────────────────────────┐
│    <<interface>>         │
│    IStoneFactory         │
├──────────────────────────┤
│ + getStone(): Stone      │
└──────────────────────────┘
         △
         │ (implements)
    ┌────┴────┐
    │         │
┌───┴────────────────┐  ┌───┴───────────────┐
│WinterStoneFactory  │  │SummerStoneFactory │
├────────────────────┤  ├───────────────────┤
│+ getStone(): Stone │  │+ getStone(): Stone│
│  → WinterStone     │  │  → SummerStone    │
│  (small/med/large) │  │  (small/med/large)│
└────────────────────┘  └───────────────────┘
```

### Background
```
┌──────────────────────────┐
│    <<interface>>         │
│      Background          │
├──────────────────────────┤
│ + render()               │
│ + getType(): String      │
└──────────────────────────┘
         △
         │ (implements)
    ┌────┴────┐
    │         │
┌───┴─────────────┐  ┌───┴──────────────┐
│Winter           │  │Summer            │
│Background       │  │Background        │
├─────────────────┤  ├──────────────────┤
│+ render()       │  │+ render()        │
│  "Snowy         │  │  "Beach with     │
│   mountains"    │  │   palm trees"    │
│+ getType()      │  │+ getType()       │
└─────────────────┘  └──────────────────┘
```

### Character
```
┌──────────────────────────┐
│    <<interface>>         │
│      Character           │
├──────────────────────────┤
│ + move()                 │
│ + getName(): String      │
└──────────────────────────┘
         △
         │ (implements)
    ┌────┴────┐
    │         │
┌───┴─────────────┐  ┌───┴──────────────┐
│Winter           │  │Summer            │
│Character        │  │Character         │
├─────────────────┤  ├──────────────────┤
│+ move()         │  │+ move()          │
│  "Penguin       │  │  "Surfer riding  │
│   sliding"      │  │   waves"         │
│+ getName()      │  │+ getName()       │
└─────────────────┘  └──────────────────┘
```

### Client
```
                    △
                    │ (uses GameFactory)
            ┌───────┴────────────┐
            │      Client        │
            │      (Main)        │
            ├────────────────────┤
            │ + initializeGame(  │
            │     GameFactory)   │
            │ + main(String[])   │
            └────────────────────┘
```

---

## Object Creation Flow

```
Step 1: Client creates factory
┌──────────────────────────────────────┐
│ GameFactory factory =                │
│   new WinterGameFactory();           │
└──────────────────────────────────────┘
                │
                ▼
Step 2: Client requests products from factory
┌──────────────────────────────────────┐
│ IStoneFactory sf =                   │
│   factory.getStoneFactory();         │
│ Background bg =                      │
│   factory.getBackground();           │
│ Character ch =                       │
│   factory.getCharacter();            │
└──────────────────────────────────────┘
                │
                ▼
Step 3: WinterGameFactory creates Winter family
┌──────────────────────────────────────┐
│ sf → WinterStoneFactory              │
│ bg → WinterBackground                │
│ ch → WinterCharacter                 │
└──────────────────────────────────────┘
                │
                ▼
Step 4: Use the products
┌──────────────────────────────────────┐
│ bg.render()  // Snowy mountains      │
│ ch.move()    // Penguin sliding      │
│ Stone s = sf.getStone() // WinterStone│
└──────────────────────────────────────┘

ALL OBJECTS FROM SAME FAMILY ✓
```

---

## Simplified Structure

```
                    GameFactory
                         │
            ┌────────────┼────────────┐
            │            │            │
     Creates │     Creates │     Creates │
            ▼            ▼            ▼
    IStoneFactory   Background   Character
            │            │            │
    ┌───────┼───────┐   │    ┌───────┼───────┐
    │               │   │    │               │
WinterStone    SummerStone │ WinterChar  SummerChar
Factory        Factory     │
                    ┌──────┼──────┐
                    │             │
              WinterBG      SummerBG
```

---

## Factory Method vs Abstract Factory

### Factory Method (Stone Example):
```
┌──────────────────┐
│  IStoneFactory   │
└────────┬─────────┘
         │ creates
         ▼
     ┌───────┐
     │ Stone │  ← Only ONE product type
     └───────┘
```

### Abstract Factory (Extended Example):
```
┌──────────────────┐
│   GameFactory    │
└────────┬─────────┘
         │ creates
         │
    ┌────┴────┬──────────┐
    ▼         ▼          ▼
┌────────┐ ┌────┐ ┌─────────┐
│IStone  │ │BG  │ │Character│  ← FAMILY of products
│Factory │ │    │ │         │
└────────┘ └────┘ └─────────┘
```

---

## Key Points

1. **GameFactory** - Abstract factory interface
2. **WinterGameFactory, SummerGameFactory** - Concrete factories
3. **Each factory creates a complete family** of related objects
4. **Client** depends only on abstract factory interface
5. **Benefit** - Ensures all objects from same theme work together
6. **Composition** - Factory creates and returns product objects (aggregation)
