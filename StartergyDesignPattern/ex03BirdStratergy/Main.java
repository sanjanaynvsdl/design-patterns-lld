package ex03BirdStratergy;

public class Main {
    public static void main(String[] args) {
        IFlyStratergy flyHigh = new FlyHigh();
        IHunStratergy gorundHunt = new GroundHunting();

        //Bird
        Bird bird  = new Bird(flyHigh, gorundHunt);
        bird.fly();
        bird.hunt();
    }
    
}
