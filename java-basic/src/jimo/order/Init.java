package jimo.order;

public class Init {
    public static <T> T init(String name, T obj) {
        System.out.println("Initializing " + name + " " + obj);
        return obj;
    }
}
