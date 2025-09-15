
# Observer Pattern - Design Pattern

## Problem Statements

### 1. Weather Application
**Scenario:** A central weather data object holds information like temperature, atmospheric pressure, and precipitation. Multiple display objects (mobile apps, web apps, public LED screens) need automatic updates when weather data changes.

**Challenge:** Design a system where displays are automatically notified and updated without tight coupling.

### 2. Student Report System
**Scenario:** A digital student results system with multiple components:
- A class storing scores for assignments, mid-terms, final exams
- Another class handling course-level mapping, grades, and attendance

**Problem:** When one component updates (e.g., midterm score changes from 50% to 80%), it should automatically trigger updates in:
- Overall grade recalculation
- CGR updates
- Scholarship eligibility reassessment
- Placement eligibility checks

**Core Challenge:** Design a system where changes in one object automatically notify and update related objects without tightly coupling them.

---

## Why Observer Pattern?

### Inefficient Approach - Polling
Running an infinite loop to continuously check for changes is highly inefficient.

### YouTube Channel Example

**Inefficient Approach (Polling):**
- Keep checking the YouTube channel repeatedly
- Set a frequency: every hour, minute, day, or week
- Manually check for new video uploads
- Resource intensive and may miss updates

**Efficient Approach (Observer Pattern):**
- Subscribe to the channel once
- Automatically receive notifications for new videos
- No manual checking required
- Real-time updates

---

## Polling vs Push Architecture

### Polling (Eager Loading)
- Client repeatedly checks a resource for updates
- Client initiates requests at regular intervals
- **Drawbacks:**
  - Inefficient - many unnecessary checks
  - May miss updates if interval is too long
  - Resource-intensive with high polling frequency

### Push - Observer Pattern (Lazy Loading)
- Subject notifies observers when updates occur
- Subject initiates communication on change
- **Benefits:**
  - Eliminates unnecessary checks
  - Real-time or near real-time updates
  - Reduces network traffic and processing overhead
  - Implements publish-subscribe model

---

## Problem with Tight Coupling

```java
// Initial approach - tightly coupled design
class Subject {
    A a;
    B b;
    C c;

    public Subject(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public setCGR() {
        // Manually calling each dependent class
        a.calculatePercentage();
        b.generateGradeSheet();
        c.updateScholarship();
    }
}

// Problems with this approach:
// 1. Constructor explosion - too many dependencies
// 2. Hard to add new subscribers - requires code modification
// 3. Tight coupling - Subject knows about all observers
// 4. Difficult to manage when methods need different arguments
```

---

## Student Problem Implementation

### Architecture Overview
Refer to the implementation files in this directory:

**Core Components:**
1. **Subject (Observable):** `Student.java`
   - Maintains list of observers
   - Notifies all observers on state change
   - Methods: `addSubscriber()`, `removeSubscriber()`, `notifySub()`

2. **Observer Interface:** `StudentObserver.java`
   - Defines contract with `update(Student student)` method

3. **Concrete Observers:**
   - `AcademicObserver.java` - Handles grade sheets and academic updates
   - `CulturalActivityObserver.java` - Manages cultural activities
   - `HostelObserver.java` - Manages hostel-related updates

4. **Client:** `Main.java`
   - Creates observers and subscribes them to the subject

### How It Works
1. Student data changes (e.g., CGR update)
2. Student calls `notifySub()` method
3. All registered observers receive the update
4. Each observer reacts according to its responsibility

### Benefits Achieved
- **Loose Coupling:** Student doesn't know concrete observer implementations
- **Dynamic Subscription:** Can add/remove observers at runtime
- **Open/Closed Principle:** Can add new observers without modifying existing code
- **Single Responsibility:** Each observer handles its specific concern

---

## Architectural Note

### Monolithic vs Microservices

**Monolithic Architecture:**
- Observer Pattern is used for in-process communication
- All components run in the same application
- Direct method calls between subject and observers

**Microservices Architecture:**
- **Pub-Sub (Publish-Subscribe)** is used instead
- Advanced version of Observer Pattern for distributed systems
- Components run as separate services
- Communication through message brokers (RabbitMQ, Kafka, etc.)
- Provides additional benefits:
  - Service isolation
  - Independent scaling
  - Fault tolerance
  - Asynchronous processing

The Observer Pattern is the foundation for understanding more complex event-driven architectures used in modern distributed systems.