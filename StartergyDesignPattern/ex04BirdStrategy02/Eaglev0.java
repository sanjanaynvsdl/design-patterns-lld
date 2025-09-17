package ex04BirdStrategy02;

//i think this is not the right way to implement the behaviours
//instead create seperate behaviours like FlyHigh, FlyLow, etc
public class Eaglev0  implements IFlyStrategy, IHuntStrategy{

    @Override
    public void fly() {
        System.out.println("eagle flies at a crayz ht!");
    }

    @Override
    public void hunt() {
        System.out.println("eagle is huntinggg!");
    }
    
    
}
