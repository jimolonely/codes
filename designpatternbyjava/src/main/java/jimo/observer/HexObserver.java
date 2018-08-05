package jimo.observer;

public class HexObserver extends Observer {
    public HexObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    void update() {
        System.out.println("Hex String: " + Integer.toHexString(subject.getState()));
    }
}
