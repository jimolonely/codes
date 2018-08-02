package jimo.order;

public class Base {

    private static int i = Init.init("base static variable i ", 1);
    private static int i1 = Init.init("base static variable i1 ", 2);
    private int j = Init.init("base instance variable j", 5);
    private int j1 = Init.init("base instance variable j1", 6);
    private int k;

    static {
        Init.init("base static block", 3);
    }

    {
        Init.init("base initialization block", 7);
    }

    public Base() {
        Init.init("base contructor k ", 8);
    }

    public void hello() {
        System.out.println("base method");
    }
}
