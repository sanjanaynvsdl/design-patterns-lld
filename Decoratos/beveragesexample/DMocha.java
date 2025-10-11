package beveragesexample;

public class DMocha extends BeverageDecorator {

   

    public DMocha(Beverage beverage) {
        super(beverage);
    }
    

    @Override
    public int cost() {
        int mochaPrice = 100;
        int totalCost = super.beverage.cost()+mochaPrice;

        return totalCost;
    }

    @Override
    public String getDescription() {
        return super.beverage.getDescription()+" , added mocha ";
        
    }

}
