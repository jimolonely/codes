package jimo.bridge;

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
