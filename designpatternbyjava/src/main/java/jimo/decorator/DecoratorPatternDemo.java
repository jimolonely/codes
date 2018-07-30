package jimo.decorator;

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
