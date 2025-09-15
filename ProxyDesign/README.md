# Proxy Design Pattern

## Problem Statement
Let's say we are part of an NLP application that needs to:
- Load lots of text data from books, newspapers, websites
- Parse and analyze this data
- Provide utility methods for text analysis

## Initial Implementation

### Case 1: Direct Object Creation

```java
class BookParser {
    String text;
    XML xtext;

    // String might be very long
    public BookParser(String book) {
        this.text = book;
        // Heavy operations in constructor:
        // 1. Parsing
        // 2. Semantic parsing
        // 3. Parts of speech analysis
        // 4. Figure out root words
        // All this happens in the constructor
        // Initialize data structures
    }

    int getWordCnt() {
        // Return word count
    }

    int getSentenceCnt() {
        // Return sentence count
    }

    boolean searchWord(String word) {
        // Search for exact word
    }

    boolean searchRootWord(String word) {
        // If input is "play", return true if "playing" is present
    }
}

// Client directly creates object
class Client {
    int fn() {
        BookParser bookP = new BookParser("book content");
        // Client is directly dependent on low-level module BookParser
        // Violates Dependency Inversion Principle
    }
}
```

### Case 2: Using Interface and Dependency Injection

```java
interface ITextParser {
    int getWordCnt();
    int getSentenceCnt();
    boolean searchWord(String word);
}

class BookParser implements ITextParser {
    // Same heavy constructor as before
    public BookParser(String book) {
        // Heavy operations...
    }
    // Implement interface methods
}

class Client {
    ITextParser textParser;

    public Client(ITextParser textParser) {
        this.textParser = textParser;
    }

    int f1() {
        return textParser.getWordCnt();
    }

    boolean f2(String word) {
        return textParser.searchWord(word);
    }

    int f3() {
        return textParser.getSentenceCnt();
    }
}
```

## Performance Issue

```java
class Main {
    public static void main(String[] args) {
        // Case 1: Direct creation (takes 1 millisecond)
        Client c1 = new Client();

        // Case 2: Dependency injection (takes 5 seconds!)
        Client c2 = new Client(new BookParser("book content"));

        // Problem: Even if we only use f1() and f3(),
        // we still pay the cost of heavy BookParser initialization
        // This is eager initialization - wasteful if not all features are used
    }
}
```

## Case 3: Attempting Lazy Initialization (Bad Design)

```java
class Client {
    ITextParser textParser;

    public Client(ITextParser textParser) {
        // Don't initialize here - trying lazy loading
    }

    int f1() {
        // Check if null, then initialize
        if(textParser == null) {
            init(); // Use separate init function
        }
        return textParser.getWordCnt();
    }

    void init() {
        textParser = factory.getBookParserInstance("book");
    }

    // Problems with this approach:
    // 1. f1() now has multiple responsibilities (violates SRP)
    // 2. Client needs to handle initialization logic
    // 3. Bad design - these checks shouldn't be on client side
}
```

## The Solution: Proxy Pattern

We don't want:
- Eager initialization (wastes resources)
- Lazy initialization on client side (violates SRP)

Solution: Create a wrapper/proxy class

```java
class BookParserProxy implements ITextParser {
    private BookParser bookParser;
    private String bookContent;

    public BookParserProxy(String bookContent) {
        // Store the content but don't parse yet
        this.bookContent = bookContent;
    }

    public int getWordCnt() {
        // Lazy initialization - create only when needed
        if(bookParser == null) {
            bookParser = new BookParser(bookContent);
        }
        return bookParser.getWordCnt();
    }

    public int getSentenceCnt() {
        if(bookParser == null) {
            bookParser = new BookParser(bookContent);
        }
        return bookParser.getSentenceCnt();
    }

    public boolean searchWord(String word) {
        if(bookParser == null) {
            bookParser = new BookParser(bookContent);
        }
        return bookParser.searchWord(word);
    }
}

// Usage
class Main {
    public static void main(String[] args) {
        // Factory creates proxy instead of actual BookParser
        ITextParser parser = new BookParserProxy("book content");

        // Client uses it normally - no changes needed
        Client client = new Client(parser);

        // BookParser is only created when first method is called
        client.f1(); // BookParser created here
        client.f3(); // Reuses existing BookParser
    }
}
```

## Key Principles

**Thumb Rule:** When changes are needed, they should be on our side - we should not ask the client to do anything special.

## Other Use Cases

### Authentication Proxy
For a PDF reader, we can create a wrapper that:
- Checks if user is authorized
- Only calls actual PDF reader if authorized
- Throws exception if unauthorized

```java
class SecurePDFReaderProxy implements IPDFReader {
    private PDFReader actualReader;
    private User user;

    public void readPDF(String file) {
        if(!user.isAuthorized()) {
            throw new UnauthorizedException();
        }
        if(actualReader == null) {
            actualReader = new PDFReader();
        }
        actualReader.readPDF(file);
    }
}
```

## This is the Proxy Design Pattern!

The Proxy Pattern provides a placeholder or surrogate for another object to control access to it. It's useful for:
- Lazy initialization (Virtual Proxy)
- Access control (Protection Proxy)
- Remote object access (Remote Proxy)
- Caching (Cache Proxy)