# Proxy Design Pattern - Class Diagrams

## UML Diagram 1 - BookParser Proxy (Virtual Proxy)

```
┌──────────────────────┐
│   <<interface>>      │
│    ITextParser       │
├──────────────────────┤
│ + getWordCnt()       │
│ + getSentenceCnt()   │
│ + searchWord(String) │
└──────────────────────┘
          △
          │ (implements)
          │
    ┌─────┴──────┐
    │            │
┌───┴────────┐  ┌┴────────────────┐
│ BookParser │  │ BookParserProxy │
├────────────┤  ├─────────────────┤
│ - text     │  │ - realParser ◆──┼──→ BookParser
├────────────┤  │ - bookContent   │     (creates lazily)
│ + BookParser(String) │ + BookParserProxy(String)
│ + getWordCnt()       │ + getWordCnt()
│ + getSentenceCnt()   │ + getSentenceCnt()
│ + searchWord()       │ + searchWord()
└──────────────┘  └─────────────────┘

         △
         │ (uses)
┌────────┴──────┐
│    Client     │
└───────────────┘

Legend:
- BookParserProxy HAS-A BookParser (composition - filled diamond ◆)
- Both implement ITextParser
- Proxy creates real object lazily (only when needed)
```

---

## UML Diagram 2 - PDF Reader Proxy (Protection Proxy)

```
┌────────────────────────┐
│    <<interface>>       │
│      IPDFReader        │
├────────────────────────┤
│ + openPDF(String)      │
│ + readPage(int)        │
│ + closePDF()           │
└────────────────────────┘
          △
          │ (implements)
    ┌─────┴────────┐
    │              │
┌───┴──────────┐  ┌┴───────────────────────┐
│  PDFReader   │  │ SecurePDFReaderProxy   │
├──────────────┤  ├────────────────────────┤
│ - currentFile│  │ - realReader ◆─────────┼──→ PDFReader
├──────────────┤  │ - currentUser          │
│ + openPDF()  │  │ - filename             │
│ + readPage() │  ├────────────────────────┤
│ + closePDF() │  │ + SecurePDFReaderProxy(User)
└──────────────┘  │ + openPDF(String)      │
                  │ + readPage(int)        │
        ┌─────────┤ + closePDF()           │
        │         └────────────────────────┘
        │
        │ (uses)
        ▼
┌──────────────┐
│     User     │
├──────────────┤
│ - name       │
│ - permissions│
├──────────────┤
│ + hasPermission(String)
│ + getName()  │
└──────────────┘

Legend:
- SecurePDFReaderProxy HAS-A PDFReader (composition ◆)
- SecurePDFReaderProxy USES User (association)
- Proxy checks permissions before delegating to real object
```

---

## Sequence Diagram - Virtual Proxy (Lazy Loading)

```
Client          BookParserProxy         BookParser
  │                    │                     │
  │ new Proxy()        │                     │
  ├───────────────────►│                     │
  │                    │ (stores content,    │
  │                    │  doesn't parse yet) │
  │                    │                     │
  │ getWordCnt()       │                     │
  ├───────────────────►│                     │
  │                    │                     │
  │                    │ First call!         │
  │                    │ Create real parser  │
  │                    ├────────────────────►│
  │                    │                     │ Heavy parsing
  │                    │                     │ happens here
  │                    │                     │
  │                    │ getWordCnt() ───────┤
  │◄───────────────────┤◄────────────────────┤
  │                    │                     │
  │ getSentenceCnt()   │                     │
  ├───────────────────►│                     │
  │                    │ Reuse existing!     │
  │                    │ getSentenceCnt()────┤
  │◄───────────────────┤◄────────────────────┤
  ▼                    ▼                     ▼

Key: Real object created lazily on first use, then reused
```

---

## Sequence Diagram - Protection Proxy (Access Control)

```
Client       SecurePDFReaderProxy    User      PDFReader
  │                 │                  │           │
  │ new Proxy(user) │                  │           │
  ├────────────────►│                  │           │
  │                 │                  │           │
  │ openPDF()       │                  │           │
  ├────────────────►│                  │           │
  │                 │ Check permission │           │
  │                 ├─────────────────►│           │
  │                 │◄─────────────────┤           │
  │                 │ Authorized ✓     │           │
  │                 │                  │           │
  │                 │ Create real reader           │
  │                 ├─────────────────────────────►│
  │                 │                  │           │
  │                 │ openPDF() ───────────────────┤
  │                 │                  │           │
  │                 │ Log access       │           │
  │◄────────────────┤◄─────────────────────────────┤
  ▼                 ▼                  ▼           ▼

Key: Proxy checks authorization before delegating
```

---

## Why Composition (◆) between Proxy and Real Object?

### Reasons:

1. **Strong Ownership:**
   - Proxy creates the real object internally
   - Real object belongs to that proxy instance
   - No one else has reference to it

2. **Lifecycle Dependency:**
   - Real object is created when proxy needs it
   - When proxy is garbage collected, real object also dies
   - They have the same lifecycle

3. **Part-Whole Relationship:**
   - Real object is PART OF the proxy's implementation
   - Proxy cannot function without access to real object
   - It's an internal component of the proxy

### NOT Aggregation because:
- Object is NOT passed from outside
- Object is NOT shared between multiple proxies
- Proxy CREATES and OWNS the real object

---

## Comparison of Proxy Types

```
┌────────────────┬─────────────────┬──────────────────────┐
│  Proxy Type    │  Purpose        │  Example             │
├────────────────┼─────────────────┼──────────────────────┤
│ Virtual Proxy  │ Lazy loading    │ BookParserProxy      │
│                │ Delay expensive │ (heavy parsing)      │
│                │ operations      │                      │
├────────────────┼─────────────────┼──────────────────────┤
│ Protection     │ Access control  │ SecurePDFReaderProxy │
│ Proxy          │ Authorization   │ (check permissions)  │
├────────────────┼─────────────────┼──────────────────────┤
│ Cache Proxy    │ Store results   │ Cache responses      │
│                │ Avoid repeated  │ from API calls       │
│                │ operations      │                      │
├────────────────┼─────────────────┼──────────────────────┤
│ Remote Proxy   │ Access remote   │ RMI, Web Services    │
│                │ objects         │                      │
└────────────────┴─────────────────┴──────────────────────┘
```

---

## Key Points

1. **Same Interface:** Proxy and Real Object implement same interface
2. **Composition:** Proxy HAS-A Real Object (creates it internally)
3. **Transparent:** Client doesn't know it's using proxy
4. **Control Access:** Proxy adds logic before/after delegating
5. **Lazy Initialization:** Create real object only when needed

---

## Benefits

- **Performance:** Virtual Proxy delays expensive operations
- **Security:** Protection Proxy controls access
- **Logging:** Can add logging without modifying real class
- **Caching:** Can cache results for repeated calls
- **Open/Closed Principle:** Add features without modifying real class
