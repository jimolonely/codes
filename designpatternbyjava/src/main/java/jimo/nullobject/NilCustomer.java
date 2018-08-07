package jimo.nullobject;

public class NilCustomer extends AbstractCustomer {
    @Override
    boolean isNil() {
        return true;
    }

    @Override
    String getName() {
        return "No Available in Customer Database";
    }
}
