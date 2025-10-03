# Flyweight Design Pattern - Class Diagram

## UML Diagram - Bullet Game Example

```
┌──────────────────────┐
│  <<interface>>       │
│     IBullet          │
├──────────────────────┤
│ + render(int x, int y)│
└──────────────────────┘
         △
         │ (implements)
         │
┌────────┴─────────────────────────┐
│      Bullet (Flyweight)          │
├──────────────────────────────────┤
│ - image: String  (intrinsic)     │ ← Shared state
│ - speed: int     (intrinsic)     │    (immutable)
│ - damage: int    (intrinsic)     │
├──────────────────────────────────┤
│ + Bullet(String, int, int)       │
│ + render(int x, int y)           │ ← Extrinsic state
└──────────────────────────────────┘    (passed as param)
         △
         │ (creates & caches)
         │
┌────────┴──────────────────────────┐
│    BulletFactory                  │
├───────────────────────────────────┤
│ - bulletCache: Map<String,IBullet>│ ← Cache of shared objects
├───────────────────────────────────┤
│ + getBullet(String type): IBullet │
│   (returns cached or creates new) │
└───────────────────────────────────┘
         △
         │ (uses)
         │
┌────────┴─────────────────────────┐
│         Game (Client)            │
├──────────────────────────────────┤
│ - bullets: List<BulletInstance>  │
├──────────────────────────────────┤
│ + fireBullet(String, int, int)   │
│ + renderAll()                    │
│                                  │
│  ┌──────────────────────────┐   │
│  │ BulletInstance (inner)   │   │
│  ├──────────────────────────┤   │
│  │ - bullet: IBullet ◇──────┼───┼──→ Shared Bullet
│  │ - x: int  (extrinsic)    │   │
│  │ - y: int  (extrinsic)    │   │
│  ├──────────────────────────┤   │
│  │ + render()               │   │
│  └──────────────────────────┘   │
└──────────────────────────────────┘
```

---

## Intrinsic vs Extrinsic State

```
┌────────────────────────────────────────┐
│           Bullet Object                │
├────────────────────────────────────────┤
│                                        │
│  INTRINSIC STATE (Shared)              │
│  ═══════════════════════                │
│  • image: "rifle.png"                  │
│  • speed: 100                          │
│  • damage: 50                          │
│                                        │
│  ✓ Same for all rifle bullets          │
│  ✓ Stored in flyweight object          │
│  ✓ Immutable                           │
│                                        │
├────────────────────────────────────────┤
│                                        │
│  EXTRINSIC STATE (Unique)              │
│  ═══════════════════                   │
│  • x: 10, 15, 20, ...                  │
│  • y: 20, 25, 30, ...                  │
│                                        │
│  ✓ Different for each bullet           │
│  ✓ Stored outside flyweight            │
│  ✓ Passed as parameter to render()     │
│                                        │
└────────────────────────────────────────┘
```

---

## Memory Comparison

### Without Flyweight (Wasteful):
```
5 Rifle Bullets = 5 Objects

Bullet1: {image: "rifle.png", speed: 100, damage: 50, x: 10, y: 20}
Bullet2: {image: "rifle.png", speed: 100, damage: 50, x: 15, y: 25}
Bullet3: {image: "rifle.png", speed: 100, damage: 50, x: 20, y: 30}
Bullet4: {image: "rifle.png", speed: 100, damage: 50, x: 25, y: 35}
Bullet5: {image: "rifle.png", speed: 100, damage: 50, x: 30, y: 40}

Memory: 5 × (image + speed + damage + x + y) = LOTS of duplicate data!
```

### With Flyweight (Efficient):
```
5 Rifle Bullets = 1 Shared Object + 5 Position Pairs

Shared Bullet: {image: "rifle.png", speed: 100, damage: 50}
Positions:
  - (10, 20)
  - (15, 25)
  - (20, 30)
  - (25, 35)
  - (30, 40)

Memory: 1 × (image + speed + damage) + 5 × (x + y) = Much less!
```

---

## Object Sharing Diagram

```
┌─────────────────────────────────────┐
│        BulletFactory Cache          │
├─────────────────────────────────────┤
│  "rifle"  ──→ [Bullet: rifle.png]  │ ← Created once
│  "pistol" ──→ [Bullet: pistol.png] │ ← Created once
│  "shotgun"──→ [Bullet: shotgun.png]│ ← Created once
└─────────────────────────────────────┘
         │          │          │
         │          │          │
    Shared by multiple bullets
         │          │          │
         ▼          ▼          ▼
┌─────────┐  ┌─────────┐  ┌─────────┐
│Bullet 1 │  │Bullet 2 │  │Bullet 3 │
│x:10,y:20│  │x:15,y:25│  │x:20,y:30│
└─────────┘  └─────────┘  └─────────┘
(All 3 share same rifle Bullet object)
```

---

## Sequence Diagram

```
Game        BulletFactory    Cache    Bullet(Flyweight)
 │               │             │            │
 │ fireBullet("rifle", 10, 20) │            │
 ├──────────────►│             │            │
 │               │ getBullet("rifle")       │
 │               ├────────────►│            │
 │               │             │            │
 │               │ Check cache │            │
 │               │◄────────────┤            │
 │               │ Not found!  │            │
 │               │             │            │
 │               │ Create new Bullet("rifle.png", 100, 50)
 │               ├────────────────────────►│
 │               │             │            │
 │               │ Store in cache           │
 │               ├────────────►│            │
 │               │◄────────────┤            │
 │◄──────────────┤             │            │
 │ Store (bullet, 10, 20)      │            │
 │               │             │            │
 │ fireBullet("rifle", 15, 25) │            │
 ├──────────────►│             │            │
 │               │ getBullet("rifle")       │
 │               ├────────────►│            │
 │               │ Found! ✓    │            │
 │               │◄────────────┤            │
 │◄──────────────┤ (reuse same object)     │
 │ Store (bullet, 15, 25)      │            │
 │               │             │            │
 ▼               ▼             ▼            ▼

Key: First bullet creates object, rest reuse it
```

---

## Code Flow

```
1. Client fires rifle bullet:
   game.fireBullet("rifle", 10, 20)

2. Game asks factory:
   bullet = BulletFactory.getBullet("rifle")

3. Factory checks cache:
   if(cache.has("rifle")) → return cached
   else → create new, cache it, return

4. Game stores:
   bullets.add(new BulletInstance(bullet, 10, 20))

5. When rendering:
   for each instance:
     instance.bullet.render(instance.x, instance.y)

   Shared object used with different positions!
```

---

## Example with Numbers

```
Scenario: 10,000 bullets in game
- 5,000 rifle bullets
- 3,000 pistol bullets
- 2,000 shotgun bullets

WITHOUT Flyweight:
10,000 Bullet objects created
Memory: 10,000 × (image + speed + damage + x + y)

WITH Flyweight:
3 shared Bullet objects (rifle, pistol, shotgun)
10,000 position pairs (x, y)
Memory: 3 × (image + speed + damage) + 10,000 × (x + y)

SAVINGS: Massive! ✓
```

---

## Key Relationships

### Composition (◆) - Factory and Flyweight:
```
BulletFactory ◆───→ Map<String, IBullet>

Factory owns and manages the cache
Flyweights are created and stored by factory
```

### Aggregation (◇) - Client and Flyweight:
```
BulletInstance ◇───→ IBullet

Client holds reference to shared flyweight
Doesn't own it - just uses it
Many clients share same flyweight
```

---

## Immutability is Key

```
✓ GOOD: Flyweight is immutable
┌────────────────────────┐
│ class Bullet {         │
│   private final String │
│     image;             │
│   private final int    │
│     speed;             │
│   private final int    │
│     damage;            │
│ }                      │
└────────────────────────┘

Can safely share between threads!


❌ BAD: Flyweight is mutable
┌────────────────────────┐
│ class Bullet {         │
│   private String image;│
│   private int speed;   │
│                        │
│   setSpeed(int s) {...}│
│ }                      │
└────────────────────────┘

Changing one affects all! Disaster!
```

---

## Benefits

1. **Memory Savings** - Share common data among many objects
2. **Performance** - Fewer objects = less memory = better performance
3. **Scalability** - Can handle thousands of objects efficiently
4. **Separation** - Clear separation of intrinsic and extrinsic state

---

## When to Use

- Many similar objects need to be created
- Most object state can be made extrinsic (shared)
- Memory is a concern
- Objects are immutable (or mostly immutable)

---

## Real-World Examples

- **Text Editor:** Character objects (same 'A' shared by all occurrences)
- **Game Trees:** Tree objects in forest (same tree model, different positions)
- **String Pool:** Java strings (same "hello" shared across program)
- **Icon Cache:** UI icons (same icon image shared by multiple buttons)

---

## Flyweight vs Prototype

| Aspect | Flyweight | Prototype |
|--------|-----------|-----------|
| **Purpose** | Share objects to save memory | Clone objects to avoid creation cost |
| **Immutability** | Must be immutable | Can be mutable |
| **State** | Intrinsic (shared) + Extrinsic (unique) | All state cloned |
| **Sharing** | One object shared by many | Each clone is independent |
