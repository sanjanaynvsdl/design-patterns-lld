package ex03BirdStratergy;


public class Pigeon  implements IFlyStratergy, IHunStratergy{

    @Override
    public void fly() {
        System.out.println("pigoen flies in medium range!");
    }

    @Override
    public void hunt() {
        System.out.println("pigeon has diff huting style -> even idk");
    }
    
}
