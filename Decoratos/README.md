# Decorator Design Pattern

## Simple Explanation (My Words)

"Sir, Decorator pattern is for adding features or behaviors to an object dynamically at runtime, without modifying the original class. It's like wrapping gifts - you start with a box, wrap it with paper, add a ribbon, then a bow. Each layer adds something new."

**Key Point:** "We wrap objects inside other objects. Each wrapper adds its own behavior and then delegates to the wrapped object."

---

## Problem Statement - Notification System

### Initial Requirement:
"Sir, we have a notification system that sends emails. Now clients want:
- Some want Email only
- Some want Email + SMS
- Some want Email + WhatsApp
- Some want Email + Slack
- Some want Email + WhatsApp + Slack (all three!)"

### Problem with Inheritance:
"If we use inheritance, we'd need classes like:
- EmailNotifier
- EmailSMSNotifier
- EmailWhatsAppNotifier
- EmailSlackNotifier
- EmailWhatsAppSlackNotifier
- EmailSMSWhatsAppNotifier
- ... and so on

For n notification types, we'd need 2^n - 1 classes! Class explosion!"

---

## Solution: Decorator Pattern

"Sir, instead of creating all combinations, we use decorators. We wrap the base notifier with decorator objects. Each decorator adds its own notification channel."

---

## Implementation

### Step 1: Component Interface

```java
// Base interface
public interface Notifier {
    void notify(String message);
}
```

### Step 2: Concrete Component (Base Email)

```java
// Base implementation
public class EmailNotifier implements Notifier {
    private String emailAddress;

    public EmailNotifier(String email) {
        this.emailAddress = email;
    }

    @Override
    public void notify(String message) {
        System.out.println("Sending Email to " + emailAddress + ": " + message);
    }
}
```

### Step 3: Base Decorator (Abstract)

```java
// Base decorator - wraps another Notifier
public abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappedNotifier;  // The object we're decorating

    public NotifierDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void notify(String message) {
        wrappedNotifier.notify(message);  // Delegate to wrapped object
    }
}
```

### Step 4: Concrete Decorators

```java
// SMS Decorator
public class SMSDecorator extends NotifierDecorator {
    private long phoneNumber;

    public SMSDecorator(Notifier notifier, long phoneNumber) {
        super(notifier);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void notify(String message) {
        super.notify(message);  // First, do what the wrapped notifier does
        sendSMS(message);       // Then, add SMS behavior
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

// WhatsApp Decorator
public class WhatsAppDecorator extends NotifierDecorator {
    private long phoneNumber;

    public WhatsAppDecorator(Notifier notifier, long phoneNumber) {
        super(notifier);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void notify(String message) {
        super.notify(message);     // Delegate to wrapped notifier
        sendWhatsApp(message);     // Add WhatsApp behavior
    }

    private void sendWhatsApp(String message) {
        System.out.println("Sending WhatsApp to " + phoneNumber + ": " + message);
    }
}

// Slack Decorator
public class SlackDecorator extends NotifierDecorator {
    private String channelName;

    public SlackDecorator(Notifier notifier, String channel) {
        super(notifier);
        this.channelName = channel;
    }

    @Override
    public void notify(String message) {
        super.notify(message);   // Delegate to wrapped notifier
        sendSlack(message);      // Add Slack behavior
    }

    private void sendSlack(String message) {
        System.out.println("Sending Slack message to #" + channelName + ": " + message);
    }
}
```

### Step 5: Client Usage

```java
public class Demo {
    public static void main(String[] args) {
        // 1. Email only (baseline)
        Notifier emailOnly = new EmailNotifier("user@example.com");
        emailOnly.notify("Baseline email only.");

        // 2. Email + SMS
        System.out.println("\n-------------Email+SMS-----------------");
        Notifier smsNotifier = new EmailNotifier("sanju@gmail.com");
        smsNotifier = new SMSDecorator(smsNotifier, 98867);
        smsNotifier.notify("Thankyou for shopping <3");

        // 3. Email + WhatsApp
        System.out.println("\n--------------------Email+WhatsApp---------------");
        Notifier whatsAppNotifier = new EmailNotifier("sanju@gmail.com");
        whatsAppNotifier = new WhatsAppDecorator(whatsAppNotifier, 78160);
        whatsAppNotifier.notify("keep Shopping!");

        // 4. Email + Slack
        System.out.println("\n----------------------Email+Slack-------------------------------");
        Notifier slackNotifier = new EmailNotifier("sanju@gmail.com");
        slackNotifier = new SlackDecorator(slackNotifier, "sst-assignments");
        slackNotifier.notify("your feedback is valuable!");

        // 5. Email + Slack + WhatsApp (Triple combo!)
        System.out.println("\n-------------------------Email+Slack+WhatsApp------------------------");
        Notifier threeNotifiers = new EmailNotifier("sanju@gmail.com");
        threeNotifiers = new SlackDecorator(threeNotifiers, "sst-assignments");
        threeNotifiers = new WhatsAppDecorator(threeNotifiers, 7816071);
        threeNotifiers.notify("testing!");
    }
}
```

---

## Output:

```
Baseline email only.
Sending Email to user@example.com: Baseline email only.

-------------Email+SMS-----------------
Sending Email to sanju@gmail.com: Thankyou for shopping <3
Sending SMS to 98867: Thankyou for shopping <3

--------------------Email+WhatsApp---------------
Sending Email to sanju@gmail.com: keep Shopping!
Sending WhatsApp to 78160: keep Shopping!

----------------------Email+Slack-------------------------------
Sending Email to sanju@gmail.com: your feedback is valuable!
Sending Slack message to #sst-assignments: your feedback is valuable!

-------------------------Email+Slack+WhatsApp------------------------
Sending Email to sanju@gmail.com: testing!
Sending Slack message to #sst-assignments: testing!
Sending WhatsApp to 7816071: testing!
```

---

## UML Diagram - Decorator Pattern (Notification System)

```
                ┌──────────────────┐
                │  <<interface>>   │
                │    Notifier      │
                ├──────────────────┤
                │ + notify(String) │
                └──────────────────┘
                        △
                        │ (implements)
          ┌─────────────┼─────────────┐
          │                           │
┌─────────┴────────────┐    ┌─────────┴──────────────┐
│  EmailNotifier       │    │ NotifierDecorator      │
│  (Concrete Component)│    │   (Base Decorator)     │
├──────────────────────┤    ├────────────────────────┤
│ - emailAddress       │    │ # wrappedNotifier ◇────┼──→ Notifier
├──────────────────────┤    ├────────────────────────┤
│ + EmailNotifier(     │    │ + NotifierDecorator(   │
│     String)          │    │     Notifier)          │
│ + notify(String)     │    │ + notify(String)       │
└──────────────────────┘    └────────────────────────┘
                                      △
                                      │ (extends)
                      ┌───────────────┼────────────────┐
                      │               │                │
          ┌───────────┴────────┐ ┌───┴──────────┐ ┌──┴─────────────┐
          │  SMSDecorator      │ │WhatsApp      │ │SlackDecorator  │
          ├────────────────────┤ │Decorator     │ ├────────────────┤
          │ - phoneNumber      │ ├──────────────┤ │ - channelName  │
          ├────────────────────┤ │- phoneNumber │ ├────────────────┤
          │ + SMSDecorator(    │ ├──────────────┤ │+ SlackDecorator│
          │     Notifier, long)│ │+ WhatsApp    │ │  (Notifier,    │
          │ + notify(String)   │ │  Decorator(  │ │   String)      │
          │ - sendSMS(String)  │ │  Notifier,   │ │+ notify(String)│
          └────────────────────┘ │  long)       │ │- sendSlack(    │
                                 │+ notify(     │ │    String)     │
                                 │   String)    │ └────────────────┘
                                 │- sendWhatsApp│
                                 │   (String)   │
                                 └──────────────┘


Wrapping Example (Email + Slack + WhatsApp):
┌────────────────────────────────────────────────────────┐
│ WhatsAppDecorator                                      │
│   wraps ──→ SlackDecorator                             │
│              wraps ──→ EmailNotifier                   │
└────────────────────────────────────────────────────────┘

When notify() is called on WhatsAppDecorator:
1. WhatsAppDecorator.notify() calls super.notify()
2. SlackDecorator.notify() calls super.notify()
3. EmailNotifier.notify() sends email (base behavior)
4. SlackDecorator.notify() sends Slack message (adds behavior)
5. WhatsAppDecorator.notify() sends WhatsApp (adds behavior)

Legend:
- All decorators implement Notifier interface
- Decorators wrap another Notifier object (aggregation ◇)
- Each decorator adds behavior and delegates to wrapped object
```

---

## How Decorator Works (Step-by-Step)

### Example: Email + SMS + WhatsApp

```java
Notifier notifier = new EmailNotifier("user@example.com");
notifier = new SMSDecorator(notifier, 98867);
notifier = new WhatsAppDecorator(notifier, 78160);
notifier.notify("Hello!");
```

**Structure created:**
```
WhatsAppDecorator
    └── wraps: SMSDecorator
            └── wraps: EmailNotifier
```

**When notify("Hello!") is called:**

1. `WhatsAppDecorator.notify("Hello!")` executes
   - Calls `super.notify("Hello!")` (delegates to SMSDecorator)
   - Then calls `sendWhatsApp("Hello!")`

2. `SMSDecorator.notify("Hello!")` executes
   - Calls `super.notify("Hello!")` (delegates to EmailNotifier)
   - Then calls `sendSMS("Hello!")`

3. `EmailNotifier.notify("Hello!")` executes
   - Sends email (base behavior)

**Result:** Email sent → SMS sent → WhatsApp sent

---

## Key Benefits of Decorator Pattern

1. **No Class Explosion**
   - Instead of 2^n classes for n notification types
   - We have 1 base + n decorators
   - Any combination possible by wrapping

2. **Open/Closed Principle**
   - Open for extension: Add new decorator (e.g., TelegramDecorator)
   - Closed for modification: Don't touch existing code

3. **Runtime Flexibility**
   - Can compose different combinations at runtime
   - Based on user preferences or configuration

4. **Single Responsibility**
   - Each decorator handles one notification channel
   - EmailNotifier only handles email
   - SMSDecorator only handles SMS

5. **Composition Over Inheritance**
   - Use wrapping instead of extending
   - More flexible than inheritance hierarchy

---

## Another Real-World Example: Coffee Shop

```java
// Base
ICoffee coffee = new SimpleCoffee();  // ₹50

// Add features dynamically
coffee = new MilkDecorator(coffee);         // ₹50 + ₹20 = ₹70
coffee = new SugarDecorator(coffee);        // ₹70 + ₹10 = ₹80
coffee = new WhippedCreamDecorator(coffee); // ₹80 + ₹30 = ₹110

System.out.println(coffee.getCost());  // 110
System.out.println(coffee.getDescription());
// "Simple Coffee + Milk + Sugar + Whipped Cream"
```

Each decorator:
- Wraps the previous coffee object
- Adds its own cost
- Adds its own description
- Delegates to wrapped object for base cost/description

---

## When to Use Decorator Pattern

**Use Decorator when:**
- You need to add responsibilities to objects dynamically
- You have many optional features/behaviors
- Inheritance would create too many subclasses
- You want to add features without modifying existing code

**Warning Signs:**
- "We need all combinations of features A, B, C"
- "Different clients need different sets of features"
- "Features should be optional and composable"

---

## Decorator vs Strategy Pattern

| Aspect | Decorator | Strategy |
|--------|-----------|----------|
| **Purpose** | Add features/wrap objects | Change algorithm/behavior |
| **Structure** | Wraps same interface | Injects different strategy |
| **Example** | Email → Email+SMS → Email+SMS+Slack | List with MergeSort or QuickSort |
| **Combination** | Can stack multiple decorators | Usually one strategy at a time |

**Decorator:** Adds layers (like an onion)
**Strategy:** Swaps implementation (like changing engine)

---

## Perfect Viva Answer

**Sir asks: "Explain Decorator Pattern with your notification example."**

**You say:**

"Sir, Decorator pattern is for adding features dynamically at runtime without modifying the original class.

In our notification system:
- Base is EmailNotifier - sends emails
- We want to add SMS, WhatsApp, Slack optionally
- Instead of creating classes for every combination (Email+SMS, Email+WhatsApp, Email+SMS+WhatsApp... class explosion!)
- We create decorator classes

Each decorator:
- Implements Notifier interface
- Wraps another Notifier object
- Adds its own behavior (SMS/WhatsApp/Slack)
- Delegates to wrapped object

For Email+SMS+WhatsApp:
```java
Notifier n = new EmailNotifier("user@example.com");
n = new SMSDecorator(n, 98867);
n = new WhatsAppDecorator(n, 78160);
n.notify("Hello!");
```

When notify() is called, it goes through all layers:
Email sent → SMS sent → WhatsApp sent

**Benefits:**
- No class explosion - just 1 base + 3 decorators instead of 7 classes
- Can create any combination at runtime
- Open/Closed Principle - add new channels without modifying existing code
- Each decorator has single responsibility"

---

## Additional Resources

For more examples, refer to:
- https://github.com/sanjanaynvsdl/LLD101-Assignment02/tree/main/beverages_decorator
- https://github.com/sanjanaynvsdl/LLD101-Assignment02/tree/main/decorator-exercises-starter
