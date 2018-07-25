package jimo.abstract_factory;

public class ShapeFactory extends AbstractFactory {
    Color getColor(String color) {
        return null;
    }

    Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equals("circle")) {
            return new Circle();
        } else if (shapeType.equals("square")) {
            return new Square();
        } else if (shapeType.equals("rectangle")) {
            return new Rectangle();
        }
        return null;
    }
}
