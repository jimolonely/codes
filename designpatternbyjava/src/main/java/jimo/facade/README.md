# Facade Pattern
Facade模式：给已存在的系统提供一个接口访问，以此隐藏复杂性。

![facade_pattern_uml_diagram](./facade_pattern_uml_diagram.jpg?raw=true)

## 1.已有系统
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
## 2.新增接口访问
隐藏实现
```java
public class ShapeMaker {
    private Circle circle;
    private Rectangle rectangle;

    public ShapeMaker() {
        this.circle = new Circle();
        this.rectangle = new Rectangle();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawCircle() {
        circle.draw();
    }
}
```
## 3.外部访问
```java
public class FacadePatternDemo {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
    }
}
```
