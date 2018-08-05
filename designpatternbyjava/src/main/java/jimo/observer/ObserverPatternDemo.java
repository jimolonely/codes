package jimo.observer;

public class ObserverPatternDemo {
    public static void main(String[] args) {
        Subject subject = new Subject();
        OctalObserver octalObserver = new OctalObserver(subject);
        BinaryObserver binaryObserver = new BinaryObserver(subject);
        HexObserver hexObserver = new HexObserver(subject);

        subject.setState(10);
        subject.setState(15);
    }
}
