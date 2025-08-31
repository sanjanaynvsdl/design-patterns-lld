package ImmuatbleObjects;

public class PaySlip {
    private final String name;
    private final String email;

    public PaySlip(String name, String email) {
        this.name=name;
        this.email=email;
    }

    private void setName(String newName) {
        this.name=newName;
    }
    

    
}
