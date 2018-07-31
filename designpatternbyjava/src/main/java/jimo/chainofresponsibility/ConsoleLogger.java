package jimo.chainofresponsibility;

public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger() {
        this.level = AbstractLogger.CONSOLE;
    }

    @Override
    protected void write(String message) {
        System.out.println("CONSOLE Logger: " + message);
    }
}
