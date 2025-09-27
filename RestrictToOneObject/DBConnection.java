
package RestrictToOneObject;
//------- Eager Loading------------

public class DBConnection {
    static DBConnection newDbconnection = new DBConnection("sql");
    String type;

    private DBConnection(String name) {
        this.type=name;
    }

    public static DBConnection getInstance() {
        return newDbconnection;
    }
    
    public static String getType() {
        return newDbconnection.type;
    }

    public String getTypeNonstatic() {
        return this.type;
    }

}



