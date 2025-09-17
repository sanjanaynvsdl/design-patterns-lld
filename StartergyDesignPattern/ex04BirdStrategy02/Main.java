package ex04BirdStrategy02;



public class Main {
    public static void main(String[] args) {
        Bird eagle = new Bird("Eagle", new FlyHigh(), new AerialHunt());
        Bird pigeon = new Bird("Pigeon", new FlyLow(), new GroundHunt());

        eagle.fly();   // Eagle: Flying high in the sky!
        eagle.hunt();  // Eagle: Hunts prey while flying.

        pigeon.fly();  // Pigeon: Flying close to the ground!
        pigeon.hunt(); // Pigeon: Hunts small insects on the ground.

        // Change behavior at runtime
        System.out.println("\nEagle got injured...");
        eagle.setFlyStrategy(new FlyLow());
        eagle.fly();  // Eagle: Flying close to the ground!
    }
}
