# Visitor Pattern
In Visitor pattern, we use a visitor class which changes the executing algorithm of an element class. By this way, execution algorithm of element can vary as and when visitor varies. This pattern comes under behavior pattern category. As per the pattern, element object has to accept the visitor object so that visitor object handles the operation on the element object.

![visitor_pattern_uml_diagram](./visitor_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface ComputerPart {
    void accept(ComputerPartVisitor visitor);
}
```
## 2.
```java
public class Computer implements ComputerPart {
    private ComputerPart[] parts;

    public Computer() {
        parts = new ComputerPart[]{new Monitor(), new Mouse(), new KeyBoard()};
    }

    @Override
    public void accept(ComputerPartVisitor visitor) {
        for (ComputerPart part : parts) {
            part.accept(visitor);
        }
        visitor.visit(this);
    }
}
```
```java
public class Monitor implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}
```
```java
public class Mouse implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}
```
```java
public class KeyBoard implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}
```
## 3.
```java
public interface ComputerPartVisitor {
    void visit(Mouse mouse);

    void visit(Computer computer);

    void visit(KeyBoard keyBoard);

    void visit(Monitor monitor);
}
```
```java
public class ComputerPartDispslayVisitor implements ComputerPartVisitor {
    @Override
    public void visit(Mouse mouse) {
        System.out.println("display mouse");
    }

    @Override
    public void visit(Computer computer) {
        System.out.println("display computer");
    }

    @Override
    public void visit(KeyBoard keyBoard) {
        System.out.println("display keyboard");
    }

    @Override
    public void visit(Monitor monitor) {
        System.out.println("display monitor");
    }
}
```
## 4.
```java
public class VisitPatternDemo {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.accept(new ComputerPartDispslayVisitor());
    }
}
/*
display monitor
display mouse
display keyboard
display computer
*/
```
