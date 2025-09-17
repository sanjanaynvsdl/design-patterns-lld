package ex04BirdStrategy02;


public class Pigeon extends Bird {
    public Pigeon() {
        super(new FlyLow(), new GroundHunt());
    }
}
