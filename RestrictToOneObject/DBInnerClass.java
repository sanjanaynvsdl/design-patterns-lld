public class DBInnerClass {
    private int type;

    public DBInnerClass(int type) {
        this.type=type;
    }

    public static DBInnerClass getInstance() {
        return helper.instance;
    }

    public int getType() {
        return this.type;
    }

    private static class helper {
        public static DBInnerClass instance = new DBInnerClass(1);
    }
}
