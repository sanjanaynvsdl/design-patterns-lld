package FactoryDesignPattern;

public class MediumSt extends Stone{


    public MediumSt(String name) {
        super(name);

    }
    @Override
    public String getName() {
        return this.name;
    }
    
}
