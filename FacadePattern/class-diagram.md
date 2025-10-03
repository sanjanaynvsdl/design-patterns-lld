# Facade Design Pattern - Class Diagram

## UML Diagram - E-commerce Order Processing

```
┌───────────────────────────────┐
│  OrderProcessorFacade         │
│        (FACADE)               │
├───────────────────────────────┤
│ - orderService ◆──────────────┼──→ OrderService
│ - paymentService ◆────────────┼──→ PaymentService
│ - notificationService ◆───────┼──→ NotificationService
│ - inventoryService ◆──────────┼──→ InventoryService
│ - receiptService ◆────────────┼──→ ReceiptService
├───────────────────────────────┤
│ + OrderProcessorFacade()      │
│ + processOrder()              │ ← Simple unified interface
└───────────────────────────────┘
         △
         │ (uses)
         │
    ┌────┴─────┐
    │  Order   │ ← Client
    │(Client)  │
    └──────────┘


SUBSYSTEM CLASSES (Complex internals):
═══════════════════════════════════════

┌──────────────┐  ┌────────────────┐  ┌──────────────────────┐
│OrderService  │  │PaymentService  │  │NotificationService   │
├──────────────┤  ├────────────────┤  ├──────────────────────┤
│+ createOrder│  │+ processPayment│  │+ sendToSeller()      │
│  ()          │  │  ()            │  │+ sendToInventory()   │
└──────────────┘  └────────────────┘  └──────────────────────┘

┌──────────────────┐  ┌─────────────────┐
│InventoryService  │  │ReceiptService   │
├──────────────────┤  ├─────────────────┤
│+ updateInventory │  │+ generateReceipt│
│  ()              │  │  ()             │
└──────────────────┘  │+ sendToBuyer()  │
                      └─────────────────┘

Legend:
- Facade owns all subsystem objects (composition ◆)
- Client only knows about Facade
- Subsystem classes are hidden from client
```

---

## Detailed Structure with Methods

```
┌────────────────────────────────────────┐
│      OrderProcessorFacade              │
├────────────────────────────────────────┤
│ - orderService: OrderService           │
│ - paymentService: PaymentService       │
│ - notificationService:                 │
│     NotificationService                │
│ - inventoryService: InventoryService   │
│ - receiptService: ReceiptService       │
├────────────────────────────────────────┤
│ + OrderProcessorFacade()               │
│   {                                    │
│     orderService = new OrderService()  │
│     paymentService = new PaymentService()
│     notificationService = new          │
│       NotificationService()            │
│     inventoryService = new             │
│       InventoryService()               │
│     receiptService = new               │
│       ReceiptService()                 │
│   }                                    │
│                                        │
│ + processOrder()                       │
│   {                                    │
│     orderService.createOrder()         │
│     paymentService.processPayment()    │
│     notificationService.               │
│       sendNotificationToSeller()       │
│     notificationService.               │
│       sendNotificationToInventory()    │
│     inventoryService.updateInventory() │
│     receiptService.generateReceipt()   │
│     receiptService.sendReceiptToBuyer()│
│   }                                    │
└────────────────────────────────────────┘
```

---

## Without Facade (Complex Client Code)

```
┌──────────────────────────────────────────┐
│            Order (Client)                │
├──────────────────────────────────────────┤
│ - orderService: OrderService             │
│ - paymentService: PaymentService         │
│ - notificationService: NotificationService│
│ - inventoryService: InventoryService     │
│ - receiptService: ReceiptService         │
├──────────────────────────────────────────┤
│ + checkout()                             │
│   {                                      │
│     // Client must know all services!    │
│     orderService.createOrder()           │
│     paymentService.processPayment()      │
│     notificationService.sendToSeller()   │
│     notificationService.sendToInventory()│
│     inventoryService.updateInventory()   │
│     receiptService.generateReceipt()     │
│     receiptService.sendToBuyer()         │
│     // 7 method calls!                   │
│     // Must know exact sequence!         │
│   }                                      │
└──────────────────────────────────────────┘

❌ Too complex!
❌ Client must know all subsystems
❌ Hard to maintain
❌ Tight coupling
```

---

## With Facade (Clean Client Code)

```
┌──────────────────────────────┐
│      Order (Client)          │
├──────────────────────────────┤
│ - facade: OrderProcessorFacade│
├──────────────────────────────┤
│ + checkout()                 │
│   {                          │
│     facade.processOrder()    │
│     // One simple call!      │
│   }                          │
└──────────────────────────────┘

✓ Simple!
✓ Client only knows Facade
✓ Easy to maintain
✓ Loose coupling
```

---

## Sequence Diagram

```
Order        OrderProcessor    Order    Payment  Notification Inventory Receipt
(Client)     Facade            Service  Service  Service      Service   Service
  │              │               │        │         │            │         │
  │ checkout()   │               │        │         │            │         │
  ├─────────────►│               │        │         │            │         │
  │              │               │        │         │            │         │
  │  processOrder()              │        │         │            │         │
  │              ├──────────────►│        │         │            │         │
  │              │ createOrder() │        │         │            │         │
  │              │               │        │         │            │         │
  │              ├───────────────────────►│         │            │         │
  │              │ processPayment()       │         │            │         │
  │              │                        │         │            │         │
  │              ├────────────────────────────────►│            │         │
  │              │ sendToSeller()                  │            │         │
  │              │                                 │            │         │
  │              ├────────────────────────────────►│            │         │
  │              │ sendToInventory()               │            │         │
  │              │                                 │            │         │
  │              ├────────────────────────────────────────────►│         │
  │              │ updateInventory()                           │         │
  │              │                                             │         │
  │              ├────────────────────────────────────────────────────►│
  │              │ generateReceipt()                                   │
  │              │                                                     │
  │              ├────────────────────────────────────────────────────►│
  │              │ sendToBuyer()                                       │
  │◄─────────────┤                                                     │
  │ Done!        │                                                     │
  ▼              ▼                                                     ▼

Client makes ONE call → Facade handles ALL complexity
```

---

## Simplified Structure

```
        Client
          │
          │ knows only
          ▼
       Facade
          │
          │ coordinates
          ▼
    ┌─────┼─────┬─────┬─────┐
    │     │     │     │     │
Service1 S2  S3  S4  S5
(Complex subsystems hidden from client)
```

---

## Key Relationships

### Composition (◆) - Facade and Subsystems:
```
Facade ◆───→ Subsystem Services

Why Composition?
- Facade creates and owns all subsystem objects
- Subsystems are part of facade's implementation
- When facade dies, subsystems die too
- Strong lifecycle dependency
```

---

## Benefits

1. **Simplification** - One simple interface to complex subsystem
2. **Decoupling** - Client doesn't depend on subsystem classes
3. **Easy to Use** - Client makes one call instead of many
4. **Maintainability** - Changes to subsystems don't affect client
5. **Encapsulation** - Hides complexity of subsystem

---

## When to Use

- Complex system with many interdependent classes
- Multiple steps required to accomplish a task
- Want to provide simple interface to complex functionality
- Need to decouple client from subsystem implementation
- Want to layer your subsystems

---

## Real-World Examples

- **Restaurant:** Customer orders food (simple) → Kitchen coordinates chef, ingredients, cooking (complex)
- **Car Start:** Turn key (simple) → Engine, fuel pump, electronics start (complex)
- **Online Shopping:** Click "Buy Now" (simple) → Payment, inventory, shipping coordinated (complex)

---

## Key Point

**Facade doesn't add new functionality - it just provides a simpler way to use existing functionality.**
