# Strategy Design Pattern - Class Diagram

## UML Diagram - List with Sorting and Printing Strategies

```
┌─────────────────────────┐
│   <<interface>>         │
│   ISortingStrategy      │
├─────────────────────────┤
│ + sort(List<Integer>)   │
└─────────────────────────┘
            △
            │ (implements)
            │
    ┌───────┼───────┬────────┐
    │       │       │        │
┌───┴────┐ ┌┴────┐ ┌┴─────┐ ┌┴────────┐
│MergeSort│ │Quick│ │Bubble│ │TimSort  │
│        │ │Sort │ │Sort  │ │         │
└────────┘ └─────┘ └──────┘ └─────────┘


┌─────────────────────────┐
│   <<interface>>         │
│   IPrintStrategy        │
├─────────────────────────┤
│ + print(List<Integer>)  │
└─────────────────────────┘
            △
            │ (implements)
            │
       ┌────┴────┐
       │         │
┌──────┴───┐ ┌──┴────────┐
│Horizontal│ │Vertical   │
│Print     │ │Print      │
└──────────┘ └───────────┘


┌──────────────────────────────┐
│         MyList               │
├──────────────────────────────┤
│ - data: List<Integer>        │
│ - sortStrategy ◆─────────────┼───→ ISortingStrategy
│ - printStrategy ◆────────────┼───→ IPrintStrategy
├──────────────────────────────┤
│ + MyList(ISortingStrategy,   │
│          IPrintStrategy)     │
│ + add(int)                   │
│ + get(int)                   │
│ + sort()                     │
│ + print()                    │
│ + setSortStrategy()          │
│ + setPrintStrategy()         │
└──────────────────────────────┘

Legend:
◆ = Composition (MyList owns the strategies)
△ = Implements (concrete classes implement interface)
```

---

## Bird Example - Strategy Pattern

```
┌──────────────────┐              ┌──────────────────┐
│ <<interface>>    │              │ <<interface>>    │
│ IFlyStrategy     │              │ IHuntStrategy    │
├──────────────────┤              ├──────────────────┤
│ + fly()          │              │ + hunt()         │
└──────────────────┘              └──────────────────┘
        △                                  △
        │                                  │
  ┌─────┼─────┐                      ┌────┴────┐
  │     │     │                      │         │
┌─┴──┐┌─┴──┐┌─┴──┐            ┌─────┴──┐ ┌───┴──────┐
│Fast││Slow││No  │            │Aerial  │ │Ground    │
│Fly ││Fly ││Fly │            │Hunt    │ │Hunt      │
└────┘└────┘└────┘            └────────┘ └──────────┘


            ┌──────────────────────────┐
            │        Bird              │
            ├──────────────────────────┤
            │ - name: String           │
            │ - flyStrategy ◆──────────┼───→ IFlyStrategy
            │ - huntStrategy ◆─────────┼───→ IHuntStrategy
            ├──────────────────────────┤
            │ + Bird(String,           │
            │       IFlyStrategy,       │
            │       IHuntStrategy)      │
            │ + fly()                  │
            │ + hunt()                 │
            └──────────────────────────┘
```

---

## How Strategy Works

### Without Strategy (Class Explosion):
```
List needs: 3 sorting × 2 printing = 6 classes!

- HorizontalMergeSort
- HorizontalQuickSort
- HorizontalBubbleSort
- VerticalMergeSort
- VerticalQuickSort
- VerticalBubbleSort

Adding TimSort? Now 8 classes!
```

### With Strategy (Clean):
```
1 List class
+ 4 Sorting strategies (MergeSort, QuickSort, BubbleSort, TimSort)
+ 2 Printing strategies (Horizontal, Vertical)

= 7 classes total, any combination possible!
```

---

## Usage Examples

### List Example:
```java
// Client 1: MergeSort + Horizontal Print
MyList list1 = new MyList(new MergeSort(), new HorizontalPrint());
list1.add(5);
list1.add(2);
list1.sort();
list1.print();

// Client 2: QuickSort + Vertical Print
MyList list2 = new MyList(new QuickSort(), new VerticalPrint());
list2.add(10);
list2.add(3);
list2.sort();
list2.print();

// Can change strategy at runtime!
list1.setSortStrategy(new BubbleSort());
```

### Bird Example:
```java
Bird eagle = new Bird("Eagle", new FastFly(), new AerialHunt());
Bird kiwi = new Bird("Kiwi", new NoFly(), new GroundHunt());
Bird pigeon = new Bird("Pigeon", new SlowFly(), new GroundHunt());

eagle.fly();    // Flying fast
eagle.hunt();   // Hunting from sky

kiwi.fly();     // Cannot fly
kiwi.hunt();    // Hunting on ground
```

---

## Composition vs Inheritance

### Inheritance (Bad):
```
         List
          ↑
    ┌─────┴─────┐
    │           │
HorizontalList VerticalList
    ↑           ↑
┌───┴───┐   ┌───┴───┐
│       │   │       │
HM  HQ  HB  VM  VQ  VB
(HorizontalMergeSort, HorizontalQuickSort, etc.)
```

### Composition (Good):
```
List HAS-A SortStrategy
List HAS-A PrintStrategy

Mix and match at runtime!
```

---

## Key Relationships

- **Composition (◆):** MyList/Bird owns the strategy objects
- **Interface:** ISortingStrategy, IPrintStrategy define contracts
- **Concrete Strategies:** Implement specific algorithms
- **Runtime Flexibility:** Can change strategies with setters

---

## Benefits

1. **No Class Explosion** - n+m classes instead of n×m
2. **Open/Closed Principle** - Add new strategies without modifying List
3. **Runtime Flexibility** - Change behavior with setters
4. **Single Responsibility** - Each strategy does one thing
5. **Composition over Inheritance** - HAS-A instead of IS-A
