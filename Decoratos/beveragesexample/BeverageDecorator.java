package beveragesexample;

public abstract class BeverageDecorator extends Beverage {

    Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        super("");
        this.beverage = beverage;

    }

    @Override
    public int cost() {
        return this.beverage.cost();
    }

    public abstract String getDescription();

}
