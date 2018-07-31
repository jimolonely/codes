# Chain of Responsibility Pattern
将责任一级一级传递下去，直到被解决。

![chain_pattern_uml_diagram](./chain_pattern_uml_diagram.jpg?raw=true)

## 1.
```java
public abstract class AbstractLogger {
    public static int CONSOLE = 1;
    public static int FILE = 2;
    public static int ERROR = 3;

    int level;

    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void write(String message);
}
```
## 2.
```java
public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger() {
        this.level = AbstractLogger.CONSOLE;
    }

    @Override
    protected void write(String message) {
        System.out.println("CONSOLE Logger: " + message);
    }
}
```
```java
public class FileLogger extends AbstractLogger {
    public FileLogger() {
        this.level = AbstractLogger.FILE;
    }

    @Override
    protected void write(String message) {
        System.out.println("FILE Logger: " + message);
    }
}
```
```java
public class ErrorLogger extends AbstractLogger {
    public ErrorLogger() {
        this.level = AbstractLogger.ERROR;
    }

    @Override
    protected void write(String message) {
        System.out.println("ERROR Logger: " + message);
    }
}
```
## 3.
```java
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

/**
* CONSOLE Logger: this is an information
  
  FILE Logger: this is a file message
  CONSOLE Logger: this is a file message
  
  ERROR Logger: this is an error!
  FILE Logger: this is an error!
  CONSOLE Logger: this is an error!
*/
```