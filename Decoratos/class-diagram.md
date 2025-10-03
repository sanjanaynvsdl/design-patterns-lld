# Decorator Design Pattern - Class Diagram

## UML Diagram - Notification System

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

Legend:
- All decorators implement Notifier interface
- Decorators wrap another Notifier object (aggregation ◇)
- Each decorator adds behavior and delegates to wrapped object
```

---

## Wrapping Structure

```
Wrapping Example (Email + Slack + WhatsApp):
┌────────────────────────────────────────────────────────┐
│ WhatsAppDecorator                                      │
│   wraps ──→ SlackDecorator                             │
│              wraps ──→ EmailNotifier                   │
└────────────────────────────────────────────────────────┘

Visual Representation:
┌─────────────────────────────────┐
│   WhatsAppDecorator             │
│  ┌──────────────────────────┐   │
│  │   SlackDecorator         │   │
│  │  ┌───────────────────┐   │   │
│  │  │  EmailNotifier    │   │   │
│  │  │  (Base)           │   │   │
│  │  └───────────────────┘   │   │
│  └──────────────────────────┘   │
└─────────────────────────────────┘

Like wrapping gifts!
Each layer adds something new
```

---

## Execution Flow

```
When notify() is called on WhatsAppDecorator:

1. WhatsAppDecorator.notify("Hello")
   ├─► calls super.notify("Hello")
   │   └─► SlackDecorator.notify("Hello")
   │       ├─► calls super.notify("Hello")
   │       │   └─► EmailNotifier.notify("Hello")
   │       │       └─► Sends Email ✓
   │       └─► sendSlack("Hello")
   │           └─► Sends Slack ✓
   └─► sendWhatsApp("Hello")
       └─► Sends WhatsApp ✓

Result: Email → Slack → WhatsApp (all three!)
```

---

## Code Structure

```
┌────────────────────────────────┐
│  Notifier notifier;            │
│                                │
│  // Build decorators           │
│  notifier = new EmailNotifier  │
│    ("user@example.com");       │
│                                │
│  notifier = new SlackDecorator │
│    (notifier, "channel");      │
│                                │
│  notifier = new WhatsApp       │
│    Decorator(notifier, 12345); │
│                                │
│  // Use                        │
│  notifier.notify("Hello!");    │
└────────────────────────────────┘

All three notifications sent!
```

---

## Without Decorator (Class Explosion)

```
For 4 notification types: Email, SMS, WhatsApp, Slack
Combinations = 2^4 - 1 = 15 classes!

- Email
- SMS
- WhatsApp
- Slack
- EmailSMS
- EmailWhatsApp
- EmailSlack
- SMSWhatsApp
- SMSSlack
- WhatsAppSlack
- EmailSMSWhatsApp
- EmailSMSSlack
- EmailWhatsAppSlack
- SMSWhatsAppSlack
- EmailSMSWhatsAppSlack

TOO MANY CLASSES! ❌
```

---

## With Decorator (Clean)

```
1 Base Component: EmailNotifier
+ 3 Decorators: SMSDecorator, WhatsAppDecorator, SlackDecorator

= 4 classes total
Any combination possible! ✓

Want Email + SMS? ✓
Want Email + WhatsApp + Slack? ✓
Want all four? ✓
```

---

## Sequence Diagram

```
Client      WhatsApp     Slack      Email
           Decorator   Decorator  Notifier
  │            │           │          │
  │ notify()   │           │          │
  ├───────────►│           │          │
  │            │           │          │
  │            │ super.notify()       │
  │            ├──────────►│          │
  │            │           │          │
  │            │           │ super.notify()
  │            │           ├─────────►│
  │            │           │          │
  │            │           │          │ Send Email
  │            │           │◄─────────┤
  │            │           │          │
  │            │           │ Send Slack
  │            │◄──────────┤          │
  │            │           │          │
  │            │ Send WhatsApp        │
  │◄───────────┤           │          │
  │            │           │          │
  ▼            ▼           ▼          ▼

Delegate first → Add own behavior
```

---

## Key Relationships

### Aggregation (◇) - Decorator and Component:
```
Decorator ◇───→ Notifier

Why Aggregation?
- Decorator wraps another Notifier
- Wrapped object can exist independently
- Can wrap different objects at runtime
- Weak "HAS-A" relationship
```

### Inheritance (△) - Decorator extends Base:
```
ConcreteDecorator extends NotifierDecorator
                  implements Notifier

Both decorator and component implement same interface
Allows transparency - client doesn't know it's decorated
```

---

## Coffee Shop Example (Another Use Case)

```
                ┌──────────────────┐
                │  <<interface>>   │
                │     ICoffee      │
                ├──────────────────┤
                │ + getCost(): int │
                │ + getDescription │
                └──────────────────┘
                        △
          ┌─────────────┼─────────────┐
          │                           │
┌─────────┴──────┐        ┌───────────┴────────┐
│ SimpleCoffee   │        │CoffeeDecorator     │
│                │        │(Abstract)          │
├────────────────┤        ├────────────────────┤
│+ getCost()     │        │# coffee ◇──────────┼─→ ICoffee
│  return 50     │        │+ CoffeeDecorator(  │
│+ getDescription│        │    ICoffee)        │
└────────────────┘        └────────────────────┘
                                    △
                  ┌─────────────────┼──────────────┐
                  │                 │              │
        ┌─────────┴──┐    ┌─────────┴───┐   ┌─────┴────────┐
        │ Milk       │    │ Sugar       │   │ Whipped      │
        │ Decorator  │    │ Decorator   │   │ Cream        │
        ├────────────┤    ├─────────────┤   ├──────────────┤
        │+ getCost() │    │+ getCost()  │   │+ getCost()   │
        │  (base+20) │    │  (base+10)  │   │  (base+30)   │
        └────────────┘    └─────────────┘   └──────────────┘

Usage:
ICoffee coffee = new SimpleCoffee();              // ₹50
coffee = new MilkDecorator(coffee);               // ₹70
coffee = new SugarDecorator(coffee);              // ₹80
coffee = new WhippedCreamDecorator(coffee);       // ₹110
```

---

## Benefits

1. **No Class Explosion** - 2^n classes → 1 + n classes
2. **Runtime Flexibility** - Add features dynamically
3. **Open/Closed Principle** - Add decorators without modifying base
4. **Single Responsibility** - Each decorator adds one feature
5. **Composition** - More flexible than inheritance

---

## Decorator vs Strategy

| Aspect | Decorator | Strategy |
|--------|-----------|----------|
| **Purpose** | Add features/wrap | Change algorithm |
| **Structure** | Wraps same interface | Injects strategy |
| **Example** | Email→Email+SMS→All | List with MergeSort or QuickSort |
| **Stacking** | Can stack multiple | Usually one at a time |

**Decorator:** Adds layers (onion)
**Strategy:** Swaps implementation (engine)

---

## Key Points

1. **Same Interface** - Decorator and component implement same interface
2. **Wrapping** - Decorator wraps another component
3. **Delegation** - Decorator delegates to wrapped object
4. **Add Behavior** - Decorator adds its own behavior before/after delegation
5. **Transparency** - Client doesn't know it's decorated
