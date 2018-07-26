package jimo.builder;

public abstract class Burger implements Item {
    public Packing packing() {
        return new Wrapper();
    }
}
