# Facade Design Pattern

## Purpose:
- Simplify complex systems by providing a unified interface to a set of interfaces in a subsystem
- Hide the complexities of the system and provide a simple interface to the client

## Problem Scenario:
In an e-commerce system, the checkout process involves multiple steps and services:
- Create order
- Process payment
- Send notifications
- Update inventory
- Generate receipt

## Before Facade (Complex Client Code):
Without the Facade pattern, the client has to directly interact with all subsystem services:

```java
class Order {
    private OrderService orderService;
    private PaymentService paymentService;
    private NotificationService notificationService;
    private InventoryService inventoryService;
    private ReceiptService receiptService;
    
    public Order() {
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();
        this.notificationService = new NotificationService();
        this.inventoryService = new InventoryService();
        this.receiptService = new ReceiptService();
    }
    
    public void checkout() {
        // Client has to know the exact sequence and handle all services
        orderService.createOrder();
        paymentService.processPayment();
        notificationService.sendNotificationToSeller();
        notificationService.sendNotificationToInventory();
        inventoryService.updateInventory();
        receiptService.generateReceipt();
        receiptService.sendReceiptToBuyer();
        
        // Client also needs to handle error cases for each service
        // Making the code complex and tightly coupled
    }
}
```

## After Facade (Simplified Client Code):
With the Facade pattern, we create a unified interface that handles all complexity:

```java
// Facade Class
class OrderProcessorFacade {
    private OrderService orderService;
    private PaymentService paymentService;
    private NotificationService notificationService;
    private InventoryService inventoryService;
    private ReceiptService receiptService;
    
    public OrderProcessorFacade() {
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();
        this.notificationService = new NotificationService();
        this.inventoryService = new InventoryService();
        this.receiptService = new ReceiptService();
    }
    
    public void processOrder() {
        orderService.createOrder();
        paymentService.processPayment();
        notificationService.sendNotificationToSeller();
        notificationService.sendNotificationToInventory();
        inventoryService.updateInventory();
        receiptService.generateReceipt();
        receiptService.sendReceiptToBuyer();
    }
}

// Simplified Client Code  
class Order {
    private OrderProcessorFacade orderProcessor;
    
    public Order() {
        this.orderProcessor = new OrderProcessorFacade();
    }
    
    public void checkout() {
        // Simple one-line call - complexity is hidden
        orderProcessor.processOrder();
    }
}
```

## How It's Used (Complete Example):
Here's how the actual client code would use the Order class:

```java
// Main class or Controller (the actual client)
public class ECommerceApp {
    public static void main(String[] args) {
        // User clicks "Place Order" button on website
        Order customerOrder = new Order();
        
        // This single call handles the entire checkout process
        customerOrder.checkout();
        
        System.out.println("Order completed successfully!");
    }
}

// Or in a web controller:
@Controller
public class OrderController {
    
    @PostMapping("/place-order")
    public String placeOrder() {
        Order order = new Order();
        order.checkout();  // All complexity handled internally
        
        return "order-success";
    }
}
```

## Benefits:
- **Simplified Interface**: Client only needs to call one method instead of managing multiple services
- **Reduced Coupling**: Client is not directly dependent on individual subsystem classes
- **Easier Maintenance**: Changes to subsystem interactions only affect the facade
- **Better Error Handling**: Facade can handle errors from multiple services in one place

## Key Point:
The `Order` class itself is **NOT** the client. The **client** is whoever creates and uses the `Order` object:
- Web controllers
- Main application classes
- Service classes
- Unit tests
- etc.

The client just calls `order.checkout()` and doesn't need to worry about the 7+ steps happening inside.