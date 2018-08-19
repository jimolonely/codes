package jimo.servicelocator;

public class InitialContext {
    public Object lookup(String jndiName) {
        if ("service1".equalsIgnoreCase(jndiName)) {
            System.out.println("looking up and create s new service1");
            return new Service1();
        } else if ("service2".equalsIgnoreCase(jndiName)) {
            System.out.println("looking up and create s new service2");
            return new Service2();
        }
        return null;
    }
}
