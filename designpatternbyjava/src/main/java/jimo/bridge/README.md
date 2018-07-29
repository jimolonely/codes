# Bridge Pattern

## 1.
```java
public interface DrawAPI {
    void drawCircle(int r, int x, int y);
}
```
```java
public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int r, int x, int y) {
        System.out.println("[Draw Green Circle(" + x + "," + y + "," + r + ")]");
    }
}
```
```java
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int r, int x, int y) {
        System.out.println("[Draw Red Circle(" + x + "," + y + "," + r + ")]");
    }
}
```
## 2
```java
public abstract class Shape {
    DrawAPI drawAPI;

    public Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();
}
```
```java
public class Circle extends Shape {
    private int r;
    private int x;
    private int y;

    public Circle(DrawAPI drawAPI, int r, int x, int y) {
        super(drawAPI);
        this.r = r;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(r, x, y);
    }
}
```
## 3
```java
public class BridgePatternDemo {
    public static void main(String[] args) {
        new Circle(new RedCircle(), 10, 3, 4).draw();
        new Circle(new GreenCircle(), 20, 4, 5).draw();
    }
}
/**
[Draw Red Circle(3,4,10)]
[Draw Green Circle(4,5,20)]
*/
```
