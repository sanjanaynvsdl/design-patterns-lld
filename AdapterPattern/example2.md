## Example 2: Employee Management System

### Problem:
- Multiple legacy employee classes with different structures
- Need a common interface for client code

### Solution:
Create an Employee interface and implement adapters for each legacy employee class.

```java
// Common interface
interface Employee {
    String getName();
    String getEmployeeId();
    // Other common methods...
}

// Legacy classes
class EmployeeCSV {
    public String name;
    public String id;
    // Other CSV-specific fields and methods...
}

class EmployeeDB {
    public String fullName;
    public String employeeNumber;
    // Other DB-specific fields and methods...
}

// Adapters
class EmployeeCSVAdapter implements Employee {
    private EmployeeCSV instance;
    
    public EmployeeCSVAdapter(EmployeeCSV instance) {
        this.instance = instance;
    }

    public String getName() {
        return instance.name;
    }

    public String getEmployeeId() {
        return instance.id;
    }
    // Implement other methods...
}

class EmployeeDBAdapter implements Employee {
    private EmployeeDB instance;
    
    public EmployeeDBAdapter(EmployeeDB instance) {
        this.instance = instance;
    }

    public String getName() {
        return instance.fullName;
    }

    public String getEmployeeId() {
        return instance.employeeNumber;
    }
    // Implement other methods...
}

// Client usage
public class EmployeeManager {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new EmployeeCSVAdapter(new EmployeeCSV()));
        employees.add(new EmployeeDBAdapter(new EmployeeDB()));
        
        // Client code works with the Employee interface
        for (Employee emp : employees) {
            System.out.println("Name: " + emp.getName() + ", ID: " + emp.getEmployeeId());
        }
    }
}
```

### Key Points:
- Common interface for different employee types
- Adapters wrap legacy classes without modifying them
- Client code works with a unified interface

---