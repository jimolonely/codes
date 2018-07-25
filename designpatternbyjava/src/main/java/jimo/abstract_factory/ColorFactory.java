package jimo.abstract_factory;

public class ColorFactory extends AbstractFactory {
    Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equals("red")) {
            return new Red();
        } else if (color.equals("blue")) {
            return new Blue();
        } else if (color.equals("green")) {
            return new Green();
        }
        return null;
    }

    Shape getShape(String shapeType) {
        return null;
    }
}
