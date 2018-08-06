# State Pattern
类的行为随状态的改变而改变。

![state_pattern_uml_diagram](./state_pattern_uml_diagram.jpg?raw=true)

## 1.State
```java
public class Context {
    private State state;

    public Context() {
        this.state = null;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
```
```java
public interface State {
    void doAction(Context context);
}
```
## 2.
```java
public class StartState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("Player is in start state");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Start State";
    }
}
```
```java
public class StopState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("Player is in stop state");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Stop State";
    }
}
```
## 3.Test
```java
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
/*
Player is in start state
Start State
Player is in stop state
Stop State
*/
```