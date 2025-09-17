package ex04BirdStrategy02;


public class FlyLow implements IFlyStrategy {
    @Override
    public void fly() {
        System.out.println("Flying close to the ground!");
    }
}