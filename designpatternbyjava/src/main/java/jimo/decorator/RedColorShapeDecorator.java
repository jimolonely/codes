package jimo.decorator;

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
