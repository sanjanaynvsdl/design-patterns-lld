package FactoryDesignPattern;

import java.util.Random;

public class RandomVersion implements IStoneFactory {
    @Override
    public Stone getStone() {

        // generate random stones
        Random rand = new Random();
        int randomNum = rand.nextInt(3); // 0, 1, or 2


        switch(randomNum) {
      case 0: return new SmallSt("small-stone");
      case 1: return new MediumSt("medium-stone");
      case 2: return new LargeSt("large-stone");
      default: throw new IllegalStateException("Invalid randomnumber");
  }

    }

}
