package ImmuatbleObjects;

public class Main {
    public static void main(String[] args) {

        PaySlip payslip = new PaySlip("sanjana", "ynv@gmail.com");
        

        //Builder pattern
        ImmutableObj immutableObj = new ImmutableObj.Builder().setName("sanjana").build();
        
        

        

        
    }
}
