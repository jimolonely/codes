#  Strategy Pattern
程序的行为可以在运行时改变。

![strategy_pattern_uml_diagram](./strategy_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface Strategy {
    int soOperation(int num1, int num2);
}
```
```java
public class AddStrategy implements Strategy {
    @Override
    public int soOperation(int num1, int num2) {
        return num1 + num2;
    }
}
```
```java
public class SubtractStrategy implements Strategy {
    @Override
    public int soOperation(int num1, int num2) {
        return num1 - num2;
    }
}
```
```java
public class MultiplyStrategy implements Strategy {
    @Override
    public int soOperation(int num1, int num2) {
        return num1 * num2;
    }
}
```
## 2.
```java
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return strategy.soOperation(num1, num2);
    }
}
```
## 3.
```java
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Context context = new Context(new AddStrategy());
        System.out.println("10+5=" + context.executeStrategy(10, 5));

        Context context1 = new Context(new SubtractStrategy());
        System.out.println("10-5=" + context1.executeStrategy(10, 5));

        Context context2 = new Context(new MultiplyStrategy());
        System.out.println("1p*5=" + context2.executeStrategy(10, 5));
    }
}
/**
10+5=15
10-5=5
1p*5=50
/
```