## Example 1: Seller Search Service Integration

### Problem:
- Existing Snapdeal seller ranking service uses Snapdeal's seller search service
- Need to integrate Exclusively's seller search service without changing existing code

### Solution:
Create an adapter interface for seller search service and implement adapters for both services.

```java
// Common interface
interface SellerSearchService {
    List<Seller> getSellersBySKU(String sku);
    // Other common methods...
}

// Snapdeal adapter
class SnapdealSearchAdapter implements SellerSearchService {
    private SnapdealSellerSearch snapdealSearch;
    
    public SnapdealSearchAdapter(SnapdealSellerSearch snapdealSearch) {
        this.snapdealSearch = snapdealSearch;
    }

    public List<Seller> getSellersBySKU(String sku) {
        return snapdealSearch.getSellersBySKU(sku);
    }
    // Implement other methods...
}

// Exclusively adapter
class ExclusivelySearchAdapter implements SellerSearchService {
    private ExclusivelySearch exclusivelySearch;
    
    public ExclusivelySearchAdapter(ExclusivelySearch exclusivelySearch) {
        this.exclusivelySearch = exclusivelySearch;
    }

    public List<Seller> getSellersBySKU(String sku) {
        return exclusivelySearch.getSellerListByExclusivelySKU(sku);
    }
    // Implement other methods...
}

// Usage in SellerRanking service
class SellerRanking {
    private SellerSearchService searchService;

    public SellerRanking(SellerSearchService searchService) {
        this.searchService = searchService;
    }
    
    public void rankSellers(String sku) {
        // Use searchService.getSellersBySKU() without knowing the actual implementation
        List<Seller> sellers = searchService.getSellersBySKU(sku);
        // Ranking logic...
    }
}
```

### Key Points:
- No changes to existing Snapdeal code
- New adapter for Exclusively search service
- SellerRanking service remains unchanged, works with both services

---
