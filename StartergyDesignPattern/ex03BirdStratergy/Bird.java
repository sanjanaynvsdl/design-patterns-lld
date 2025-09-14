package ex03BirdStratergy;

public class Bird {

    IFlyStratergy flyStratergy;
    IHunStratergy hunStratergy;

    public Bird(IFlyStratergy flyStratergy, IHunStratergy hunStratergy) {
        this.flyStratergy=flyStratergy;
        this.hunStratergy=hunStratergy;
    }

    public void hunt() {
        this.hunStratergy.hunt();
    }

    public void fly() {
        this.flyStratergy.fly();
    }
    
}
