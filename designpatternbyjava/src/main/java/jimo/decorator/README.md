# Decorator Pattern
在不改变原代码情况下增加新的功能。下面可以给Shape增加颜色，大小等功能，只需要增加相应的Decorator类。

![decorator_pattern_uml_diagram](./decorator_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface Shape {
    void draw();
}
```
```java
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Draw Circle");
    }
}
```
```java
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Draw Rectangle");
    }
}
```
## 2
```java
public abstract class ShapeDecorator implements Shape {
    Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }
}
```
```java
public class RedColorShapeDecorator extends ShapeDecorator {
    public RedColorShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedColor(decoratedShape);
    }

    private void setRedColor(Shape shape) {
        System.out.println("Red Shape Color");
    }
}
```
## 3
```java
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        Circle circle = new Circle();
        Shape redColorRectangle = new RedColorShapeDecorator(rectangle);
        Shape redColorCircle = new RedColorShapeDecorator(circle);

        rectangle.draw();

        redColorCircle.draw();

        redColorRectangle.draw();
    }
}
/**
* Draw Rectangle
* 
  Draw Circle
  Red Shape Color
  
  Draw Rectangle
  Red Shape Color
*/
```