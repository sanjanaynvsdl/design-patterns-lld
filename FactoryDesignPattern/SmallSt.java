package FactoryDesignPattern;

public class SmallSt extends Stone{

   

    public SmallSt(String name){
        super(name);

    }

    @Override
    public String getName() {
        return this.name;
    }
    
}
