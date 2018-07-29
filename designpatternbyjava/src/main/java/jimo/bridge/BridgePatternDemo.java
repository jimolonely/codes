package jimo.bridge;

public class BridgePatternDemo {
    public static void main(String[] args) {
        new Circle(new RedCircle(), 10, 3, 4).draw();
        new Circle(new GreenCircle(), 20, 4, 5).draw();
    }
}
