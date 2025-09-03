package FactoryDesignPattern;

public class LargeSt extends Stone {


    public LargeSt(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    
}
