package ex04BirdStrategy02;

public class Bird {
    String name;
     IFlyStrategy flyStrategy;
     IHuntStrategy huntStrategy;

    public Bird(String name, IFlyStrategy flyStrategy, IHuntStrategy huntStrategy) {
        this.flyStrategy = flyStrategy;
        this.huntStrategy = huntStrategy;
        this.name=name;
    }
    
    public Bird(IFlyStrategy flyStrategy, IHuntStrategy huntStrategy) {
        this.flyStrategy = flyStrategy;
        this.huntStrategy = huntStrategy;
        // this.name=name;
    }

    public void fly() {
        System.out.println(this.name + " : is flying!");
        flyStrategy.fly();
    }

    public void hunt() {
        huntStrategy.hunt();
    }

    // You can also change behavior at runtime!
    public void setFlyStrategy(IFlyStrategy flyStrategy) {
        this.flyStrategy = flyStrategy;
    }

    public void setHuntStrategy(IHuntStrategy huntStrategy) {
        this.huntStrategy = huntStrategy;
    }
}

