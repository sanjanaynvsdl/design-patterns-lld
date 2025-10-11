# Beverage Decorator Pattern Example

## Problem Statement

You're building a coffee shop application where customers can order beverages (like Espresso, HouseBlend) and add condiments (like Milk, Mocha, Whip) to them.

### The Challenge
- Each beverage can have multiple condiments added to it
- Each condiment adds to the cost and description
- We need a flexible way to add condiments without creating a class explosion

**Example:** If you have 4 beverages and 5 condiments, without the decorator pattern you'd need 4 × 2^5 = 128 classes for all combinations! 😱

### Why Not Multiple Inheritance?
Java allows **multi-level inheritance** (A → B → C) but **NOT multiple inheritance** (A → B and A → C).

With decorators, we cleverly use:
- **Two-level inheritance**: `Espresso → Beverage` and `DMilk → BeverageDecorator → Beverage`
- **Composition**: Each decorator wraps another Beverage object

This gives us the flexibility of multiple inheritance without actually using it!

---

## UML Class Diagram

```
┌──────────────────────────────┐
│      Beverage (abstract)     │
├──────────────────────────────┤
│ - description: String        │
├──────────────────────────────┤
│ + cost(): int                │
│ + getDescription(): String   │
└──────────────▲───────────────┘
               │
       ┌───────┴────────┐
       │                │
┌──────┴────────┐  ┌────┴─────────────────────────┐
│   Espresso    │  │ BeverageDecorator (abstract) │
├───────────────┤  ├──────────────────────────────┤
│ + cost(): int │  │ - beverage: Beverage ◇───────┼──┐
└───────────────┘  ├──────────────────────────────┤  │
                   │ + getDescription(): String   │  │
                   │ + cost(): int                │  │
                   └──────────────▲───────────────┘  │
                                  │                  │
                          ┌───────┴────────┐         │
                          │                │         │
                    ┌─────┴──────┐   ┌────┴──────┐  │
                    │   DMilk    │   │  DMocha   │  │
                    ├────────────┤   ├───────────┤  │
                    │ + cost()   │   │ + cost()  │  │
                    │ + getDesc()│   │ + getDesc()│ │
                    └────────────┘   └───────────┘  │
                          │                │         │
                          └────────────────┴─────────┘
                           (composition - wraps Beverage)
```

**Key Points:**
- `DMilk` and `DMocha` **IS-A** Beverage (through inheritance)
- `DMilk` and `DMocha` **HAS-A** Beverage (through composition - see ◇ symbol)
- This is **two-level inheritance**: `DMilk → BeverageDecorator → Beverage`

---

## Step-by-Step Execution Flow

### Code:
```java
Beverage expresso = new Espresso();
expresso = new DMilk(expresso);
expresso = new DMocha(expresso);
expresso = new DMocha(expresso);

String finalCoffe = expresso.getDescription();
int totalCost = expresso.cost();
```

### Step 1: `Beverage expresso = new Espresso();`
```
expresso → [Espresso Object]
           description: "Espresso"
           cost(): 20
```

**Memory:** Just one Espresso object.

---

### Step 2: `expresso = new DMilk(expresso);`
```
expresso → [DMilk Object]
           ├─ beverage → [Espresso Object]
           │             description: "Espresso"
           │             cost(): 20
           │
           └─ when you call:
              - getDescription() → "Espresso" + ", Milk"
              - cost() → 20 + 20 = 40
```

**Memory:** DMilk wraps Espresso (like a gift wrapper around a box).

**Object Structure:**
```
┌─────────────────┐
│  DMilk          │
│  (wrapper)      │
│  ┌───────────┐  │
│  │ Espresso  │  │
│  │ (core)    │  │
│  └───────────┘  │
└─────────────────┘
```

---

### Step 3: `expresso = new DMocha(expresso);`
```
expresso → [DMocha Object]
           ├─ beverage → [DMilk Object]
           │             ├─ beverage → [Espresso Object]
           │             │             description: "Espresso"
           │             │             cost(): 20
           │             │
           │             └─ adds: ", Milk" and +20
           │
           └─ when you call:
              - getDescription() → ("Espresso, Milk") + ", Mocha"
              - cost() → 40 + 100 = 140
```

**Memory:** DMocha wraps (DMilk which wraps Espresso).

**Object Structure:**
```
┌─────────────────────────┐
│  DMocha                 │
│  (wrapper)              │
│  ┌─────────────────┐    │
│  │  DMilk          │    │
│  │  (wrapper)      │    │
│  │  ┌───────────┐  │    │
│  │  │ Espresso  │  │    │
│  │  │ (core)    │  │    │
│  │  └───────────┘  │    │
│  └─────────────────┘    │
└─────────────────────────┘
```

---

### Step 4: `expresso = new DMocha(expresso);` (Second Mocha)
```
expresso → [DMocha Object #2]
           ├─ beverage → [DMocha Object #1]
           │             ├─ beverage → [DMilk Object]
           │             │             ├─ beverage → [Espresso Object]
           │             │             │             description: "Espresso"
           │             │             │             cost(): 20
           │             │             │
           │             │             └─ adds: ", Milk" and +20
           │             │
           │             └─ adds: ", Mocha" and +100
           │
           └─ when you call:
              - getDescription() → ("Espresso, Milk, Mocha") + ", Mocha"
              - cost() → 140 + 100 = 240
```

**Memory:** Another DMocha wraps everything.

**Object Structure (Russian Nesting Dolls!):**
```
┌───────────────────────────────┐
│  DMocha #2                    │
│  (wrapper)                    │
│  ┌─────────────────────────┐  │
│  │  DMocha #1              │  │
│  │  (wrapper)              │  │
│  │  ┌─────────────────┐    │  │
│  │  │  DMilk          │    │  │
│  │  │  (wrapper)      │    │  │
│  │  │  ┌───────────┐  │    │  │
│  │  │  │ Espresso  │  │    │  │
│  │  │  │ (core)    │  │    │  │
│  │  │  └───────────┘  │    │  │
│  │  └─────────────────┘    │  │
│  └─────────────────────────┘  │
└───────────────────────────────┘
```

---

## How Method Calls Work (The Magic! ✨)

### When you call `expresso.cost()`:

```
DMocha#2.cost()
  → calls beverage.cost() + 100
  → DMocha#1.cost() + 100
     → calls beverage.cost() + 100
     → DMilk.cost() + 100
        → calls beverage.cost() + 20
        → Espresso.cost() + 20
           → returns 20
        ← returns 20 + 20 = 40
     ← returns 40 + 100 = 140
  ← returns 140 + 100 = 240

Final Result: 240
```

### When you call `expresso.getDescription()`:

```
DMocha#2.getDescription()
  → calls beverage.getDescription() + ", Mocha"
  → DMocha#1.getDescription() + ", Mocha"
     → calls beverage.getDescription() + ", Mocha"
     → DMilk.getDescription() + ", Mocha"
        → calls beverage.getDescription() + ", Milk"
        → Espresso.getDescription() + ", Milk"
           → returns "Espresso"
        ← returns "Espresso, Milk"
     ← returns "Espresso, Milk, Mocha"
  ← returns "Espresso, Milk, Mocha, Mocha"

Final Result: "Espresso, Milk, Mocha, Mocha"
```

---

## Why It's NOT Two Different Roots

You might think Espresso and DMilk are from different inheritance trees, but:

### Class Hierarchy (Inheritance - IS-A):
```
      Beverage (abstract)
          ↑
    ┌─────┴─────┐
    │           │
 Espresso   BeverageDecorator (abstract)
                ↑
            ┌───┴───┐
          DMilk   DMocha
```

All classes share **one root**: `Beverage`

### Object Composition (HAS-A) at Runtime:
```
DMocha (wrapper) HAS-A
  └─ Beverage → DMocha (wrapper) HAS-A
                  └─ Beverage → DMilk (wrapper) HAS-A
                                  └─ Beverage → Espresso (core)
```

**The Trick:**
- Every decorator **IS-A** Beverage (through inheritance)
- Every decorator **HAS-A** Beverage (through composition)
- This is called **two-level inheritance** because decorators inherit from BeverageDecorator which inherits from Beverage

---

## Simple Analogy 🎁

Think of wrapping a gift:
1. **Core gift** = Espresso (the actual coffee)
2. **First wrapper** = DMilk wraps the Espresso
3. **Second wrapper** = DMocha wraps (DMilk + Espresso)
4. **Third wrapper** = Another DMocha wraps everything

When someone asks:
- **"What's inside?"** → Each wrapper adds its name to the description
- **"What's the total cost?"** → Each wrapper adds its cost to whatever's inside!

You can't see the inner layers directly, but when you ask questions, each layer delegates to the layer inside and adds its own contribution!

---

## Benefits of Decorator Pattern

✅ **Open/Closed Principle**: Open for extension, closed for modification
✅ **Flexibility**: Add/remove decorators at runtime
✅ **No Class Explosion**: Avoid creating `EspressoWithMilk`, `EspressoWithMocha`, `EspressoWithMilkAndMocha`, etc.
✅ **Single Responsibility**: Each decorator adds ONE specific behavior

---

## Output

```
Espresso, Milk, Mocha, Mocha
Price is 240
```

**Breakdown:**
- Espresso: 20
- Milk: +20 = 40
- Mocha: +100 = 140
- Mocha: +100 = 240
