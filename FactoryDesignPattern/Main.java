package FactoryDesignPattern;

public class Main {

    static void render(IStoneFactory sf) {
        Stone newStone = sf.getStone();
        System.out.println(newStone.getName());

    }

    public static void main(String[] args) {
        // IStoneFactory sf = new BalancedVersion();
        IStoneFactory sf = new RandomVersion();
        // render(sf);

        for (int i = 0; i < 6; i++) {
            render(sf);
        }
    }
}
