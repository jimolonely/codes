package jimo.chainofresponsibility;

public class FileLogger extends AbstractLogger {
    public FileLogger() {
        this.level = AbstractLogger.FILE;
    }

    @Override
    protected void write(String message) {
        System.out.println("FILE Logger: " + message);
    }
}
