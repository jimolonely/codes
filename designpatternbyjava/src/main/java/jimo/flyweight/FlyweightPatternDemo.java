package jimo.flyweight;

public class FlyweightPatternDemo {
    public static void main(String[] args) {
        String[] colors = {"red", "blue", "green", "pink", "yellow"};
        for (int i = 0; i < 20; i++) {
            Circle circle = ShapeFactory.getCircle(colors[(int) (Math.random() * colors.length)]);
            circle.setR(100);
            circle.setX((int) (Math.random() * 100));
            circle.setY((int) (Math.random() * 100));
            circle.draw();
        }
    }
}
