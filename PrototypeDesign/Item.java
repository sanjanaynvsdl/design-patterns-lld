package PrototypeDesign;

public abstract class Item implements IClonnable{
    protected int sellingPrize;
    protected int costPrize;
    protected int discount;

      public Item() {
        
    }
    

    public Item(int sp, int cp, int dis) {
        this.sellingPrize=sp;
        this.costPrize=cp;
        this.discount=dis;
    }

  
    public abstract Item clone();
}
