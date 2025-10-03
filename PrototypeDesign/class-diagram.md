# Prototype Design Pattern - Class Diagram

## UML Diagram - Book/Item Prototype Implementation

```
┌─────────────────────┐
│   <<interface>>     │
│    IClonnable       │
├─────────────────────┤
│ + clone(): Item     │
└─────────────────────┘
          △
          │ (implements)
          │
┌─────────┴────────────────────┐
│     Item (Abstract)          │
├──────────────────────────────┤
│ # sellingPrize: int          │
│ # costPrize: int             │
│ # discount: int              │
├──────────────────────────────┤
│ + Item()                     │
│ + Item(int, int, int)        │
│ + clone(): Item (abstract)   │
└──────────────────────────────┘
          △
          │ (extends)
    ┌─────┴─────┐
    │           │
┌───┴────────────────┐  ┌────┴──────────────────┐
│      Book          │  │       Movie           │
├────────────────────┤  ├───────────────────────┤
│ - author: String   │  │ - name: String        │
│                    │  │ - time: int           │
├────────────────────┤  ├───────────────────────┤
│ + Book()           │  │ + Movie()             │
│ + Book(int, int,   │  │ + Movie(int, int,     │
│   int, String)     │  │   int, String, int)   │
│ + clone(): Item    │  │ + clone(): Item       │
└────────────────────┘  └───────────────────────┘
          △                      △
          │                      │
          └──────────┬───────────┘
                     │ (stores prototypes)
                     │
        ┌────────────┴──────────────────────┐
        │      LoadRegistry                 │
        ├───────────────────────────────────┤
        │ - map: HashMap<String, Item>      │
        ├───────────────────────────────────┤
        │ + loadRegistry()                  │
        │   (creates Book & Movie prototypes)│
        │ + getClone(String): Item          │
        │   (returns cloned object)         │
        └───────────────────────────────────┘
                     △
                     │ (uses)
                     │
              ┌──────┴──────┐
              │    Main     │
              │  (Client)   │
              └─────────────┘
```

---

## Simplified Visual

```
        IClonnable (interface)
              ↑
              │
         Item (abstract)
              ↑
         ┌────┴────┐
         │         │
       Book      Movie
         │         │
         └────┬────┘
              │
       LoadRegistry
       stores prototypes
       returns clones
              │
            Main
          (client)
```

---

## How it Works

1. **LoadRegistry** creates prototype objects:
   - Book: (299, 200, 10, "xyz-author")
   - Movie: (599, 400, 15, "Action film", 2)

2. Stores them in HashMap with keys "book" and "movie"

3. Client calls `getClone("book")` → returns cloned Book object

4. Each `clone()` creates new object and copies all fields

---

## Example Flow

```
registry.getClone("book")
    ↓
map.get("book").clone()
    ↓
Book.clone() creates new Book and copies fields
    ↓
Returns independent Book object
```

---

## Relationships

- **IClonnable (interface)** ─→ Defines clone contract
- **Item (abstract)** ─→ Implements IClonnable, has common fields
- **Book, Movie** ─→ Extend Item, implement clone()
- **LoadRegistry** ─→ Stores prototypes, returns clones
- **Main (Client)** ─→ Uses registry to get clones

---

## Key Points

- **Composition:** LoadRegistry HAS-A map of Items (aggregation ◇)
- **Implementation:** Book and Movie implement clone() method
- **Benefit:** Don't recreate objects from scratch, just clone and modify
