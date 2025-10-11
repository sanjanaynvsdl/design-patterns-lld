package beveragesexample;

public class Espresso extends Beverage {

    public Espresso() {
        super("Espresso Coffee");
    }

    @Override
    public int cost() {
        return 20;
    }

}
