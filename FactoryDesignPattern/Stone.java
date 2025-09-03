package FactoryDesignPattern;

public abstract class Stone {
    protected String name;

    public Stone(String name) {
        this.name = name;
    }

    public abstract String getName();

}
