package beveragesexample;

public class Main{
    public static void main(String[] args) {

        Beverage expresso = new Espresso();
        expresso = new DMilk(expresso);
        expresso = new DMocha(expresso);
        expresso = new DMocha(expresso);

        String finalCoffe = expresso.getDescription();
        int totalCost = expresso.cost();
        System.out.println(finalCoffe);
        System.out.println("Price is " + totalCost);

 
        
        
    }
}



