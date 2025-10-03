# Adapter Design Pattern - Class Diagrams

## Example 1: Seller Search Service Integration

```
┌───────────────────────────┐
│     <<interface>>         │
│  SellerSearchService      │
├───────────────────────────┤
│ + getSellersBySKU(String) │
│   : List<Seller>          │
└───────────────────────────┘
            △
            │ (implements)
    ┌───────┼────────┐
    │                │
┌───┴─────────────────────┐  ┌────┴──────────────────────┐
│SnapdealSearchAdapter    │  │ExclusivelySearchAdapter   │
├─────────────────────────┤  ├───────────────────────────┤
│- snapdealSearch ◇───────┼──┤- exclusivelySearch ◇──────┼──┐
├─────────────────────────┤  ├───────────────────────────┤  │
│+ SnapdealSearchAdapter( │  │+ ExclusivelySearchAdapter(│  │
│   SnapdealSellerSearch) │  │   ExclusivelySearch)      │  │
│+ getSellersBySKU(String)│  │+ getSellersBySKU(String)  │  │
│   : List<Seller>        │  │   : List<Seller>          │  │
└─────────────────────────┘  └───────────────────────────┘  │
         │                                                   │
         │                                                   │
         ▼                                                   ▼
┌──────────────────────────┐       ┌──────────────────────────┐
│SnapdealSellerSearch      │       │  ExclusivelySearch       │
│  (External Service)      │       │  (External Service)      │
├──────────────────────────┤       ├──────────────────────────┤
│+ getSellersBySKU(String) │       │+ getSellerListBy         │
│   : List<Seller>         │       │  ExclusivelySKU(String)  │
│   (Snapdeal's method)    │       │   : List<Seller>         │
└──────────────────────────┘       └──────────────────────────┘


        ┌──────────────────────────┐
        │    SellerRanking         │
        │      (Client)            │
        ├──────────────────────────┤
        │- searchService ◇─────────┼──→ SellerSearchService
        ├──────────────────────────┤
        │+ SellerRanking(          │
        │   SellerSearchService)   │
        │+ rankSellers(String sku) │
        └──────────────────────────┘

Legend:
- Client depends on interface (SellerSearchService)
- Adapters convert between interface and external services
- Aggregation ◇: Adapters hold reference to external services
```

---

## Example 2: Employee Management System

```
┌───────────────────────┐
│   <<interface>>       │
│      Employee         │
├───────────────────────┤
│ + getName(): String   │
│ + getEmployeeId():    │
│     String            │
└───────────────────────┘
          △
          │ (implements)
    ┌─────┼──────┐
    │            │
┌───┴──────────────────┐    ┌────┴─────────────────┐
│EmployeeCSVAdapter    │    │EmployeeDBAdapter     │
├──────────────────────┤    ├──────────────────────┤
│- instance ◇──────────┼──┐ │- instance ◇──────────┼──┐
├──────────────────────┤  │ ├──────────────────────┤  │
│+ EmployeeCSVAdapter( │  │ │+ EmployeeDBAdapter(  │  │
│   EmployeeCSV)       │  │ │   EmployeeDB)        │  │
│+ getName(): String   │  │ │+ getName(): String   │  │
│+ getEmployeeId():    │  │ │+ getEmployeeId():    │  │
│    String            │  │ │    String            │  │
└──────────────────────┘  │ └──────────────────────┘  │
                          │                           │
                          ▼                           ▼
                ┌──────────────────┐    ┌──────────────────┐
                │  EmployeeCSV     │    │   EmployeeDB     │
                │  (Legacy Class)  │    │  (Legacy Class)  │
                ├──────────────────┤    ├──────────────────┤
                │+ name: String    │    │+ fullName: String│
                │+ id: String      │    │+ employeeNumber: │
                │                  │    │    String        │
                └──────────────────┘    └──────────────────┘


        ┌─────────────────────────────────┐
        │     EmployeeManager             │
        │        (Client)                 │
        ├─────────────────────────────────┤
        │+ main(String[] args)            │
        │  Creates List<Employee>         │
        │  Uses Employee interface only   │
        └─────────────────────────────────┘

Key Points:
- Two legacy classes with different field names
- Adapters map legacy fields to common interface
- Client works with unified Employee interface
```

---

## Example 3: Payment Service Integration

```
┌─────────────────────────────┐
│     <<interface>>           │
│     PaymentAdapter          │
├─────────────────────────────┤
│ + makePayment(double,       │
│     String)                 │
│ + isPaymentSuccessful():    │
│     boolean                 │
└─────────────────────────────┘
            △
            │ (implements)
    ┌───────┼────────┐
    │                │
┌───┴────────────────────┐    ┌────┴──────────────────┐
│  StripeAdapter         │    │  PayPalAdapter        │
├────────────────────────┤    ├───────────────────────┤
│- stripe ◇──────────────┼──┐ │- paypal ◇─────────────┼──┐
├────────────────────────┤  │ ├───────────────────────┤  │
│+ StripeAdapter(Stripe) │  │ │+ PayPalAdapter(PayPal)│  │
│+ makePayment(double,   │  │ │+ makePayment(double,  │  │
│    String)             │  │ │    String)            │  │
│+ isPaymentSuccessful():│  │ │+ isPaymentSuccessful()│  │
│    boolean             │  │ │    : boolean          │  │
│  (calls checkStatus()) │  │ │  (converts status)    │  │
└────────────────────────┘  │ └───────────────────────┘  │
                            │                            │
                            ▼                            ▼
                ┌──────────────────┐      ┌──────────────────────┐
                │     Stripe       │      │      PayPal          │
                │ (External API)   │      │  (External API)      │
                ├──────────────────┤      ├──────────────────────┤
                │+ makePayment(    │      │+ processPayment(     │
                │   double, String)│      │   double)            │
                │+ checkStatus():  │      │+ getTransaction      │
                │   boolean        │      │  Status(): String    │
                └──────────────────┘      └──────────────────────┘


        ┌────────────────────────────────┐
        │    PaymentProcessor            │
        │       (Client)                 │
        ├────────────────────────────────┤
        │- paymentAdapter ◇──────────────┼──→ PaymentAdapter
        ├────────────────────────────────┤
        │+ PaymentProcessor(             │
        │    PaymentAdapter)             │
        │+ processOrder(double, String)  │
        └────────────────────────────────┘

Key Differences Adapted:
┌──────────────┬─────────────────────┬──────────────────────┐
│   Method     │  Stripe             │  PayPal              │
├──────────────┼─────────────────────┼──────────────────────┤
│ Payment      │ makePayment()       │ processPayment()     │
│ Status       │ checkStatus()       │ getTransactionStatus()│
│              │ returns boolean     │ returns String       │
└──────────────┴─────────────────────┴──────────────────────┘
```

---

## Common Adapter Pattern Structure

```
┌─────────────────┐
│   <<interface>> │
│  Target Interface│  ← What client expects
└─────────────────┘
       △
       │
  ┌────┴────┐
  │         │
┌─┴──────┐ ┌┴────────┐
│Adapter1│ │Adapter2 │  ← Converts incompatible to compatible
└────────┘ └─────────┘
    │         │
    ▼         ▼
┌───────┐ ┌───────┐
│Service│ │Service│       ← External/Legacy services
│   1   │ │   2   │           (Incompatible APIs)
└───────┘ └───────┘
```

---

## Key Relationships

### Aggregation (◇) - Adapter and Adaptee:
```
Adapter ◇───→ External Service

Why Aggregation?
- External service can exist independently
- Adapter doesn't own the service
- Service might be used by other clients too
- Weak relationship
```

### Implementation (△) - Adapter and Interface:
```
Adapter ----△ Target Interface

Adapter implements target interface
Client knows only the interface
```

---

## Benefits

1. **Reusability** - Use existing classes with incompatible interfaces
2. **Separation of Concerns** - Interface conversion separated from business logic
3. **Open/Closed Principle** - Add new adapters without modifying existing code
4. **Flexibility** - Easy to switch between implementations
5. **Integration** - Connect to third-party libraries and legacy code

---

## When to Use

- Integrating third-party libraries with different interfaces
- Working with legacy code that can't be modified
- Creating unified interface for multiple similar but incompatible classes
- Making incompatible interfaces work together

---

## Real-World Analogy

**Power Adapter:**
- US plug (Adaptee) won't fit Indian socket (Target Interface)
- Power adapter (Adapter) converts between them
- Allows US device to work with Indian electricity
