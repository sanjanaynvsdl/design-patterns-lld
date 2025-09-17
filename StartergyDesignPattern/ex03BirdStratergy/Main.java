package ex03BirdStratergy;

public class Main {
    public static void main(String[] args) {
        // IFlyStratergy flyHigh = new FlyHigh();
        // IHunStratergy gorundHunt = new GroundHunting();
        //--above example is incorrect as we need to create concrete classes of birds not its behavour

        IFlyStratergy pigeonFly = new Pigeon();
        IHunStratergy pigeonHunt = new Pigeon();

        //eagle
        IFlyStratergy eagleFLy = new Eagle();
        IHunStratergy eagleHunt = new Eagle();

        //Bird
        // Bird bird  = new Bird(flyHigh, gorundHunt);
        Bird eagle = new Bird(eagleFLy,eagleHunt);
        eagle.fly();
        eagle.hunt();
    }
    
}
