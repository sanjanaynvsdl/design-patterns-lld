package ImmuatbleObjects;

public class PaySlip {
    private final String name;
    private final String email;

    public PaySlip(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //how do i return a deep copy, 
    //what is the issue if i use direct get? -coz it retuns the refernce to the obj

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}
