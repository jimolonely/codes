package jimo.nullobject;

public class CustomerFactory {
    private static final String[] names = {"Rob", "Joe", "julia"};

    public static AbstractCustomer getCustomer(String name) {
        for (String n : names) {
            if (n.equalsIgnoreCase(name)) {
                return new RealCustomer(n);
            }
        }
        return new NilCustomer();
    }
}
