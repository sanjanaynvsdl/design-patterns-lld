package ex04BirdStrategy02;


//i think this is not the right way to implement the behaviours
//instead create seperate behaviours like FlyHigh, FlyLow, etc
//refer readme.md
public class Pigeonv0  implements IFlyStrategy, IHuntStrategy{

    @Override
    public void fly() {
        System.out.println("pigoen flies in medium range!");
    }

    @Override
    public void hunt() {
        System.out.println("pigeon has diff huting style -> even idk");
    }
    
}
