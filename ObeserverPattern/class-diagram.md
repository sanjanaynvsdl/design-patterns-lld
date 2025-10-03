# Observer Design Pattern - Class Diagram

## UML Diagram - Student Observer Pattern

```
┌─────────────┐
│   Student   │◇────────────────┐
└─────────────┘                 │
      △                         │
      │                         ▼
      │                  ┌──────────────────┐
      │                  │ StudentObserver  │ (interface)
      │                  └──────────────────┘
      │                          △
      │ (dependency)             │ (realization)
      │                          │
      │              ┌───────────┼────────────┐
      │              │           │            │
      │    ┌─────────────┐ ┌──────────┐ ┌─────────────┐
      └────│AcademicObs  │ │HostelObs │ │CulturalObs  │
           └─────────────┘ └──────────┘ └─────────────┘
```

---

## Detailed UML with Methods

```
┌──────────────────────────────┐
│         Student              │
│        (Subject)             │
├──────────────────────────────┤
│ - name: String               │
│ - cgr: double                │
│ - observers: List<           │
│   StudentObserver> ◇─────────┼──┐
├──────────────────────────────┤  │
│ + Student(String, double)    │  │
│ + getCgr(): double           │  │
│ + setCgr(double)             │  │
│ + addSubscriber(             │  │
│     StudentObserver)         │  │
│ + removeSubscriber(          │  │
│     StudentObserver)         │  │
│ + notifySub()                │  │
└──────────────────────────────┘  │
                                  │
                                  ▼
┌────────────────────────────────────┐
│     <<interface>>                  │
│     StudentObserver                │
├────────────────────────────────────┤
│ + update(Student student)          │
└────────────────────────────────────┘
               △
               │ (implements)
               │
    ┌──────────┼──────────┬───────────┐
    │          │          │           │
┌───┴──────────────┐ ┌───┴────────┐ ┌┴──────────────────┐
│ AcademicObserver │ │HostelObs   │ │CulturalActivity   │
│                  │ │            │ │Observer           │
├──────────────────┤ ├────────────┤ ├───────────────────┤
│+ update(Student) │ │+ update(   │ │+ update(Student)  │
│  - Generate      │ │  Student)  │ │  - Check          │
│    grade sheet   │ │  - Update  │ │    eligibility    │
│  - Update        │ │    hostel  │ │    for cultural   │
│    scholarship   │ │    info    │ │    activities     │
└──────────────────┘ └────────────┘ └───────────────────┘
```

---

## UML Relationships

### Aggregation (◇) - Student and Observers:
```
Student ◇───→ List<StudentObserver>

Why Aggregation?
- Observers can exist independently without Student
- They can be added/removed dynamically (subscribe/unsubscribe)
- Observers are not "part of" Student - they just observe it
- If Student is destroyed, observers still exist
```

### Realization (dashed arrow) - Interface Implementation:
```
AcademicObserver ----▷ StudentObserver
HostelObserver   ----▷ StudentObserver
CulturalObserver ----▷ StudentObserver

(dashed line with hollow triangle)
```

---

## Sequence Diagram - How it Works

```
Student              AcademicObs      HostelObs      CulturalObs
  │                       │               │               │
  │ setCgr(80%)           │               │               │
  ├──────────────────────►│               │               │
  │                       │               │               │
  │ notifySub()           │               │               │
  ├───────────────────────┼───────────────┼───────────────┤
  │                       │               │               │
  │ update(this) ─────────┤               │               │
  │                       ├──► Generate   │               │
  │                       │    grade sheet│               │
  │                       │               │               │
  │ update(this) ─────────┼───────────────┤               │
  │                       │               ├──► Update     │
  │                       │               │    hostel info│
  │                       │               │               │
  │ update(this) ─────────┼───────────────┼───────────────┤
  │                       │               │               ├──► Check
  │                       │               │               │    cultural
  │                       │               │               │    eligibility
  ▼                       ▼               ▼               ▼
```

---

## Simplified Structure

```
     Subject (Student)
            │
            │ maintains list of
            │
            ▼
    List<Observer>
            │
            │ notifies all
            │
    ┌───────┼────────┐
    │       │        │
Observer1 Observer2 Observer3
(Academic)(Hostel)(Cultural)
```

---

## Key Components

1. **Subject (Student):**
   - Maintains list of observers
   - Has `addSubscriber()`, `removeSubscriber()`, `notifySub()` methods
   - When state changes (CGR), calls `notifySub()`

2. **Observer Interface (StudentObserver):**
   - Defines `update(Student)` contract
   - All observers must implement this

3. **Concrete Observers:**
   - AcademicObserver: Generates grade sheet, updates scholarship
   - HostelObserver: Updates hostel information
   - CulturalActivityObserver: Checks cultural eligibility

4. **Client (Main):**
   - Creates observers
   - Subscribes them to student
   - Changes student state
   - All observers automatically notified

---

## Benefits

1. **Loose Coupling:** Student doesn't know concrete observer types
2. **Dynamic Subscription:** Add/remove observers at runtime
3. **Open/Closed Principle:** Add new observers without modifying Student
4. **Single Responsibility:** Each observer handles its own concern
5. **Push Architecture:** Subject pushes updates, observers don't poll

---

## Real-World Examples

- **YouTube:** Subscribe to channel, get notified on new videos
- **Email Newsletter:** Subscribe, get updates automatically
- **Stock Market:** Investors observe stock prices
- **Weather App:** Users get weather change notifications
