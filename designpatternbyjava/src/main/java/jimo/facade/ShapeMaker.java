package jimo.facade;

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
