# Singleton Design Pattern

Restrict class to create only ONE object throughout application.

## Use Cases
- Database Connection Pool
- Logger 
- Configuration Manager

## Approaches

### 1. Eager Loading
- Private constructor
- Static instance created at class load
- Public static getInstance() method
- Thread-safe but wastes memory if unused

```java
private static DBConnection instance = new DBConnection("sql");
private DBConnection(String type) { ... }
public static DBConnection getInstance() { return instance; }
```

### 2. Lazy Loading  
- Create instance only when needed
- Not thread-safe - multiple objects possible

```java
private static DBConnection instance = null;
public static DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection("mongo");
    }
    return instance;
}
```

### 3. Synchronized Method
- Make getInstance() synchronized
- Thread-safe but slow performance

```java
public static synchronized DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection("mongo");
    }
    return instance;
}
```

### 4. Double-Checked Locking
- Check null twice with synchronized block
- Requires volatile keyword for CPU cache consistency

```java
private static volatile DBConnection instance;

public static DBConnection getInstance() {
    if (instance == null) {
        synchronized(DBConnection.class) {
            if (instance == null) {
                instance = new DBConnection();
            }
        }
    }
    return instance;
}
```

### 5. Enum Singleton (Recommended)
- JVM guarantees single instance
- Thread-safe, reflection-proof, serialization-safe

```java
public enum DBConnectionEnum {
    INSTANCE("sql");
    
    private String type;
    
    DBConnectionEnum(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
}

// Usage: DBConnectionEnum.INSTANCE
```

### 6. Inner Class (Bill Pugh Solution)
- Lazy loading with thread safety
- No synchronization overhead after initialization

```java
public class DBConnectionInnerClass {
    private String type;
    
    private DBConnectionInnerClass(String type) {
        this.type = type;
    }
    
    public static DBConnectionInnerClass getInstance() {
        return SingleTonHelper.instance;
    }
    
    private static class SingleTonHelper {
        private static final DBConnectionInnerClass instance = 
            new DBConnectionInnerClass("sql");
    }
} 