package ex03BirdStratergy;


public class Eagle  implements IFlyStratergy, IHunStratergy{

    @Override
    public void fly() {
        System.out.println("eagle flies at a crayz ht!");
    }

    @Override
    public void hunt() {
        System.out.println("eagle is huntinggg!");
    }
    
    
}
