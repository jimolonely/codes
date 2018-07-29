package jimo.bridge;

public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int r, int x, int y) {
        System.out.println("[Draw Red Circle(" + x + "," + y + "," + r + ")]");
    }
}
