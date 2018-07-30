package jimo.flyweight;

import java.util.HashMap;
import java.util.Map;

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
