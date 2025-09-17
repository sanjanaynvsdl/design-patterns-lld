package ex03BirdStratergy;

//probably a inncorrect solution refer ex04BirdStrategy02, readme.md
public class Main {
    public static void main(String[] args) {

        //case-1
        IFlyStratergy flyHigh = new FlyHigh();
        IHunStratergy gorundHunt = new GroundHunting();
        


        //case-2
        // IFlyStratergy pigeonFly = new Pigeonv0();
        // IHunStratergy pigeonHunt = new Pigeonv0();

        // //eagle
        // IFlyStratergy eagleFLy = new Eaglev0();
        // IHunStratergy eagleHunt = new Eaglev0();

        //------i think even case1 is right,
        

        //Bird
        // Bird bird  = new Bird(flyHigh, gorundHunt);
        Bird eagle = new Bird(flyHigh,gorundHunt);
        eagle.fly();
        eagle.hunt();

        //also, for those cases 0> where we want to crearte Eagle, Pigron concreate objects
        //refer ex04BirdStrategy02 folder, in StrategyDesingPattern
    }
    
}
