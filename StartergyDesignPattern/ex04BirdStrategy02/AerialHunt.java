package ex04BirdStrategy02;

public class AerialHunt implements IHuntStrategy {
    @Override
    public void hunt() {
        System.out.println("Hunts prey while flying.");
    }
}