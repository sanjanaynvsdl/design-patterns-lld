package RestrictToOneObject;

public enum DBConnectionEnum {
    INSTANCE("sql");
    private String type;

    //enum constructors are always private
    DBConnectionEnum(String type) {
        this.type=type;
    }

    public String getType() {
        return this.type;
    }

    //this doesn't even need a sstatic method
    //as we can directly refer DBCOnnectionEnum.INSTANCE;
    /*
     * JVM guarantees only ONE INSTANCE exists      
        - Thread-safe automatically
     */

  
}
