# Abstract Factory Pattern

![img](./abstractfactory_pattern_uml_diagram.jpg?raw=true)

## 1.
```java
public interface Shape {
    void draw();
}
```
```java
public class Square implements Shape {
    public void draw() {
        System.out.println("draw() Square");
    }
}
```
```java
public class Circle implements Shape {

    public void draw() {
        System.out.println("draw() Circle");
    }
}
```
```java
public class Rectangle implements Shape {

    public void draw() {
        System.out.println("draw() rectangle");
    }

}
```
## 2.
```java
public interface Color {
    void fill();
}
```
```java
public class Blue implements Color {

    public void fill() {
        System.out.println("fill blue color");
    }
}
```
```java
public class Red implements Color {

    public void fill() {
        System.out.println("fill red color");
    }
}
```
```java
public class Green implements Color {

    public void fill() {
        System.out.println("fill green color");
    }
}
```
## 3.
```java
public abstract class AbstractFactory {
    abstract Color getColor(String color);

    abstract Shape getShape(String shapeType);
}
```
```java
public class ShapeFactory extends AbstractFactory {
    Color getColor(String color) {
        return null;
    }

    Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equals("circle")) {
            return new Circle();
        } else if (shapeType.equals("square")) {
            return new Square();
        } else if (shapeType.equals("rectangle")) {
            return new Rectangle();
        }
        return null;
    }
}
```
```java
public class ColorFactory extends AbstractFactory {
    Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equals("red")) {
            return new Red();
        } else if (color.equals("blue")) {
            return new Blue();
        } else if (color.equals("green")) {
            return new Green();
        }
        return null;
    }

    Shape getShape(String shapeType) {
        return null;
    }
}
```
## 4.
```java
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if ("shape".equals(choice)) {
            return new ShapeFactory();
        } else if ("color".equals(choice)) {
            return new ColorFactory();
        } else {
            return null;
        }
    }
}
```
## 5.
```java
public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {

        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        Shape circle = shapeFactory.getShape("circle");
        circle.draw();

        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        Color blue = colorFactory.getColor("blue");
        blue.fill();
    }
}
```
