package ex04BirdStrategy02;

// Hunting behaviors
public class GroundHunt implements IHuntStrategy {
    @Override
    public void hunt() {
        System.out.println("Hunts small insects on the ground.");
    }
}