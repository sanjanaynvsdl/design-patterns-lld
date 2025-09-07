

## Example 3: Payment Service Integration

### Problem:
- Multiple payment service providers with different APIs
- Need to switch between providers without changing client code

### Solution:
Create a common payment adapter interface and implement adapters for each payment service.

```java
// Common interface
interface PaymentAdapter {
    void makePayment(double amount, String currency);
    boolean isPaymentSuccessful();
    // Other common payment methods...
}

// Stripe payment (external library)
class Stripe {
    public void makePayment(double amount, String currency) {
        System.out.println("Processing $" + amount + " payment via Stripe");
        // Stripe-specific implementation
    }
    
    public boolean checkStatus() {
        return true; // Stripe-specific status check
    }
}

// PayPal payment (external library)
class PayPal {
    public void processPayment(double amount) {
        System.out.println("Processing $" + amount + " payment via PayPal");
        // PayPal-specific implementation
    }
    
    public String getTransactionStatus() {
        return "SUCCESS"; // PayPal-specific status format
    }
}

// Stripe adapter
class StripeAdapter implements PaymentAdapter {
    private Stripe stripe;
    
    public StripeAdapter(Stripe stripe) {
        this.stripe = stripe;
    }
    
    public void makePayment(double amount, String currency) {
        stripe.makePayment(amount, currency);
    }
    
    public boolean isPaymentSuccessful() {
        return stripe.checkStatus();
    }
}

// PayPal adapter
class PayPalAdapter implements PaymentAdapter {
    private PayPal paypal;
    
    public PayPalAdapter(PayPal paypal) {
        this.paypal = paypal;
    }
    
    public void makePayment(double amount, String currency) {
        paypal.processPayment(amount);
    }
    
    public boolean isPaymentSuccessful() {
        return "SUCCESS".equals(paypal.getTransactionStatus());
    }
}

// Client usage
public class PaymentProcessor {
    private PaymentAdapter paymentAdapter;
    
    public PaymentProcessor(PaymentAdapter paymentAdapter) {
        this.paymentAdapter = paymentAdapter;
    }
    
    public void processOrder(double amount, String currency) {
        paymentAdapter.makePayment(amount, currency);
        
        if (paymentAdapter.isPaymentSuccessful()) {
            System.out.println("Payment processed successfully!");
        } else {
            System.out.println("Payment failed!");
        }
    }
    
    public static void main(String[] args) {
        // Can switch between different payment providers easily
        PaymentProcessor processor1 = new PaymentProcessor(new StripeAdapter(new Stripe()));
        PaymentProcessor processor2 = new PaymentProcessor(new PayPalAdapter(new PayPal()));
        
        processor1.processOrder(100.0, "USD");
        processor2.processOrder(50.0, "USD");
    }
}
```

### Key Points:
- Unified interface for different payment providers
- Easy switching between payment services
- No changes required in client code when adding new providers

---
