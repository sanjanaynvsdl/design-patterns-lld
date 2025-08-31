package RestrictToOneObject;

public class DBConnectionInnerClass {
    private String type;

    DBConnectionInnerClass(String type) {
        this.type = type;
    }

    public static DBConnectionInnerClass getInstance() {
        return SingleTonHelper.instnace;
    }
    public String getType() {
        return this.type;
    }

    private static class SingleTonHelper {
        private static final DBConnectionInnerClass instnace = new DBConnectionInnerClass("sql");
    }
}
