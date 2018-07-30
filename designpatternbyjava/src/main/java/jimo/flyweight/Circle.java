package jimo.flyweight;

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
