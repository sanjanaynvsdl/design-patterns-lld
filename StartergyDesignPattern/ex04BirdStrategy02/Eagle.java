package ex04BirdStrategy02;

public class Eagle extends Bird {
    public Eagle() {
        super(new FlyHigh(), new AerialHunt());
    }
}