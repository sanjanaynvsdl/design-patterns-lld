package FactoryDesignPattern;

import java.util.LinkedList;
import java.util.Queue;

public class BalancedVersion implements IStoneFactory {

    private Queue<String> stoneQueue = new LinkedList<>(){{
        add("Small");
        add("Medium");
        add("Large");

    }};

    @Override
    public Stone getStone() {
        String newStone = stoneQueue.poll();
        stoneQueue.add(newStone);

        System.out.println("Balanced Version stone! " + newStone);
        if (newStone.equals("Small")) {
            return new SmallSt("small-stone");
        } else if (newStone.equals("Medium")) {
            return new MediumSt("medium-stone");

        } else if (newStone.equals("Large")) {
            return new LargeSt("large-stone");
        }
        return new LargeSt("dummy");
    }
}
