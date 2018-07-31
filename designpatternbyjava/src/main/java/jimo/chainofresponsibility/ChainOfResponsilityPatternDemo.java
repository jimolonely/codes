package jimo.chainofresponsibility;

public class ChainOfResponsilityPatternDemo {

    public static AbstractLogger getChainLogger() {
        ConsoleLogger consoleLogger = new ConsoleLogger();
        FileLogger fileLogger = new FileLogger();
        ErrorLogger errorLogger = new ErrorLogger();

        fileLogger.setNextLogger(consoleLogger);
        errorLogger.setNextLogger(fileLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger chainLogger = getChainLogger();

        chainLogger.logMessage(AbstractLogger.CONSOLE, "this is an information");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.FILE, "this is a file message");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.ERROR, "this is an error!");
    }
}
