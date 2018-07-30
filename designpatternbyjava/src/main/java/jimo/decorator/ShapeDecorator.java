package jimo.decorator;

public abstract class ShapeDecorator implements Shape {
    Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }
}
