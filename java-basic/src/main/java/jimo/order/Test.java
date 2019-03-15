package jimo.order;

public class Test {
    public static void main(String[] args) {
        System.out.println("First new Sub():");
        Sub sub = new Sub();
        sub.hello();
        System.out.println("\nSecond new Sub():");
        new Sub();
        System.out.println("\nFirst new Base():");
        new Base();
        System.out.println("\nSecond new Base():");
        new Base();
    }
}
