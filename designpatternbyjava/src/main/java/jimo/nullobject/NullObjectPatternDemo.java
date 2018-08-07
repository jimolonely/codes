package jimo.nullobject;

public class NullObjectPatternDemo {
    public static void main(String[] args) {
        AbstractCustomer jimo = CustomerFactory.getCustomer("jimo");
        AbstractCustomer rob = CustomerFactory.getCustomer("Rob");
        AbstractCustomer hehe = CustomerFactory.getCustomer("hehe");

        System.out.println(jimo.getName());
        System.out.println(rob.getName());
        System.out.println(hehe.getName());
    }
}
