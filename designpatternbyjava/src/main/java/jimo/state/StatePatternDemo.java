package jimo.state;

public class StatePatternDemo {
    public static void main(String[] args) {
        StartState startState = new StartState();
        StopState stopState = new StopState();
        Context context = new Context();

        startState.doAction(context);
        System.out.println(context.getState().toString());

        stopState.doAction(context);
        System.out.println(context.getState().toString());
    }
}
