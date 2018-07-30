# Flyweight Pattern
Flyweight模式通过存储已有对象减少对象的创建，从而减少内存和资源开销。

![flyweight_pattern_uml_diagram](./flyweight_pattern_uml_diagram.jpg?raw=true)

## 1.画圆
```java
public interface Shape {
    void draw();
}
```
```java
public class Circle implements Shape {
    private String color;
    private int x;
    private int y;
    private int r;

    public Circle(String color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public void draw() {
        System.out.println("Draw Circle[Color:" + color + ",x:" + x + ",y:" + y + ",r:" + r + "]");
    }
}
```
## 2.缓存对象
```java
public class ShapeFactory {
    private static final Map<String, Circle> shapes = new HashMap<>();

    public static Circle getCircle(String color) {
        Circle circle = shapes.get(color);
        if (circle == null) {
            circle = new Circle(color);
            shapes.put(color, circle);
            System.out.println("Creating circle of color: " + color + " ...............");
        }
        return circle;
    }
}
```
## 3.测试
```java
public class FlyweightPatternDemo {
    public static void main(String[] args) {
        String[] colors = {"red", "blue", "green", "pink", "yellow"};
        for (int i = 0; i < 20; i++) {
            Circle circle = ShapeFactory.getCircle(colors[(int) (Math.random() * colors.length)]);
            circle.setR(100);
            circle.setX((int) (Math.random() * 100));
            circle.setY((int) (Math.random() * 100));
            circle.draw();
        }
    }
}
/**
* Creating circle of color: yellow ...............
  Draw Circle[Color:yellow,x:20,y:38,r:100]
  Creating circle of color: blue ...............
  Draw Circle[Color:blue,x:44,y:1,r:100]
  Creating circle of color: red ...............
  Draw Circle[Color:red,x:9,y:32,r:100]
  Creating circle of color: pink ...............
  Draw Circle[Color:pink,x:35,y:85,r:100]
  Draw Circle[Color:blue,x:12,y:45,r:100]
  Draw Circle[Color:blue,x:13,y:4,r:100]
  Draw Circle[Color:red,x:56,y:32,r:100]
  Draw Circle[Color:yellow,x:78,y:22,r:100]
  Draw Circle[Color:pink,x:15,y:22,r:100]
  Draw Circle[Color:pink,x:62,y:90,r:100]
  Creating circle of color: green ...............
  Draw Circle[Color:green,x:30,y:53,r:100]
  Draw Circle[Color:yellow,x:5,y:47,r:100]
  Draw Circle[Color:green,x:68,y:94,r:100]
  Draw Circle[Color:blue,x:94,y:10,r:100]
  Draw Circle[Color:green,x:48,y:95,r:100]
  Draw Circle[Color:pink,x:53,y:93,r:100]
  Draw Circle[Color:yellow,x:85,y:74,r:100]
  Draw Circle[Color:yellow,x:76,y:42,r:100]
  Draw Circle[Color:red,x:70,y:8,r:100]
  Draw Circle[Color:blue,x:64,y:5,r:100]
*/
/
```