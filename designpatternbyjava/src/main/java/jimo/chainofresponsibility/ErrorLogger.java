package jimo.chainofresponsibility;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger() {
        this.level = AbstractLogger.ERROR;
    }

    @Override
    protected void write(String message) {
        System.out.println("ERROR Logger: " + message);
    }
}
