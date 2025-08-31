package RestrictToOneObject;

public class DBConnectionLazy {
    // static DBConnectionLazy newDB = new DBConnectionLazy("mongo");
    // to avoid unnecceary creation of the obj when class is being loaded


    private static volatile DBConnectionLazy newDB;
    private String type;

    private DBConnectionLazy(String type) {
        this.type = type;
    }

    // type-1 create obj, whenever needed
    public static DBConnectionLazy getInstance1() {
        if (newDB == null) {
            newDB = new DBConnectionLazy("mongo");
            return newDB;

        } else {
            return newDB;
        }
        // Now, this code has thread saftey issue
        // multiple threads calling this at a time, can end up creating multiple
        // objects.
    }

    // type-2 make function synchronized but, this makes it slower
    public static synchronized DBConnectionLazy getInstance2() {
        if (newDB == null) {
            newDB = new DBConnectionLazy("mongo");
            return newDB;
        } else {
            return newDB;
        }
    }

    // type-3 make that specific block synchronized 
    public static DBConnectionLazy getInstance3() {
        if (newDB == null) {
            synchronized(DBConnectionLazy.class) {
                if(newDB==null) {
                    newDB = new DBConnectionLazy("mongo");
                }
                return newDB;
            }
        } else {
            return newDB;
        }
    }

    public String getType() {
        return this.type;
    }

    // staic method, as we get the ref of the obj, using above func
    // we can use both the static and non-static methods, on this obj
}



