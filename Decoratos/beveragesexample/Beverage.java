package beveragesexample;

public abstract class Beverage {
    private String descrption;

    public Beverage(String description) {
        this.descrption=description;
    }


    public abstract int cost();

    public String getDescription() {
        return this.descrption;
    }
}
