package ex03BirdStratergy;

//probably a inncorrect solution refer ex04BirdStrategy02, readme.md
public class Pigeon  implements IFlyStrategy, IHuntStrategy{

    @Override
    public void fly() {
        System.out.println("pigoen flies in medium range!");
    }

    @Override
    public void hunt() {
        System.out.println("pigeon has diff huting style -> even idk");
    }
    
}
