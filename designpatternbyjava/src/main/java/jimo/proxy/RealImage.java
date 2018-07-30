package jimo.proxy;

public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadImageFromDisk(fileName);
    }

    private void loadImageFromDisk(String fileName) {
        System.out.println("Loading from disk: " + fileName);
    }

    @Override
    public void display() {
        System.out.println("Display Image: " + fileName);
    }
}
