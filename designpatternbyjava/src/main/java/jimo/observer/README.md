# Observer Pattern
用于一个对象改变，一系列对象跟着改变的情况。

![observer_pattern_uml_diagram](./observer_pattern_uml_diagram.jpg?raw=true)

## 1.subject
```java
import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
```
## 2.Observers
```java
public abstract class Observer {
    protected Subject subject;

    abstract void update();
}
```
```java
public class OctalObserver extends Observer {
    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    void update() {
        System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
    }
}
```
```java
public class BinaryObserver extends Observer {
    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    void update() {
        System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
    }
}
```
```java
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
```
## 3.Test
```java
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
/**
* Octal String: 12
  Binary String: 1010
  Hex String: a

  Octal String: 17
  Binary String: 1111
  Hex String: f
*/
```