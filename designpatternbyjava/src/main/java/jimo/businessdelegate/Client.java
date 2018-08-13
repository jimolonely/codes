package jimo.businessdelegate;

public class Client {
    private BusinessDelegate delegate;

    public Client(BusinessDelegate delegate) {
        this.delegate = delegate;
    }

    public void doTask() {
        delegate.doTask();
    }
}
