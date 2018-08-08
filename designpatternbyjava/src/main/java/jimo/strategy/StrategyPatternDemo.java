package jimo.strategy;

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
