
# Factory Method

![img](./factory_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface Shape {
    void draw();
}
```
## 2
```java
public class Square implements Shape {
    public void draw() {
        System.out.println("draw() Square");
    }
}
```
## 3
```java
public class Circle implements Shape {

    public void draw() {
        System.out.println("draw() Circle");
    }
}
```
## 4
```java
public class Rectangle implements Shape {

    public void draw() {
        System.out.println("draw() rectangle");
    }

}
```
## 5
```java
public class ShapeFactory {

    public Shape getShape(String shapeType) {
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
## 6.use
```java
public class FactoryMain {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        Shape circle = factory.getShape("circle");
        circle.draw();

        Shape rectangle = factory.getShape("rectangle");
        rectangle.draw();

        Shape square = factory.getShape("square");
        square.draw();
    }
}
```