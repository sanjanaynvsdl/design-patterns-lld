package beveragesexample;

public class DMilk extends BeverageDecorator {


    public DMilk(Beverage beverage) {
        super(beverage);
        
    }
    
    @Override
    public int cost() {
        int milkPrice = 20;
        int totalCost = super.beverage.cost()+milkPrice;
        return totalCost;
    }

    @Override
    public String getDescription() {
        return super.beverage.getDescription()+" added milk";
    }

}
