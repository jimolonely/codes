package jimo.bridge;

public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int r, int x, int y) {
        System.out.println("[Draw Green Circle(" + x + "," + y + "," + r + ")]");
    }
}
