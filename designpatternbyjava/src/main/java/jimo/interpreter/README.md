# Interpreter Pattern
解释器模式提供了一种评估语言语法或表达的方法。这种模式属于行为模式。此模式涉及实现表达式接口，该接口用于解释特定上下文。此模式用于SQL解析，符号处理引擎等。

![interpreter_pattern_uml_diagram](./interpreter_pattern_uml_diagram.jpg?raw=true)

## 1
```java
public interface Expression {
    boolean interpreter(String context);
}
```
## 2
```java
public class AndExpression implements Expression {
    private Expression expression1;
    private Expression expression2;

    public AndExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean interpreter(String context) {
        return expression1.interpreter(context) && expression2.interpreter(context);
    }
}
```
```java
public class OrExpression implements Expression {
    private Expression expression1;
    private Expression expression2;

    public OrExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean interpreter(String context) {
        return expression1.interpreter(context) || expression2.interpreter(context);
    }
}
```
```java
public class TerminalExpression implements Expression {
    private String data;

    public TerminalExpression(String data) {
        this.data = data;
    }

    @Override
    public boolean interpreter(String context) {
        return context.contains(data);
    }
}
```
## 3
```java
public class InterpreterPatternDemo {
    static Expression getMaleExpression() {
        TerminalExpression john = new TerminalExpression("John");
        TerminalExpression robert = new TerminalExpression("Robert");
        return new OrExpression(john, robert);
    }

    static Expression getMarriedExpression() {
        TerminalExpression julia = new TerminalExpression("Julia");
        TerminalExpression married = new TerminalExpression("Married");
        return new AndExpression(julia, married);
    }

    public static void main(String[] args) {
        Expression isMale = getMaleExpression();
        Expression isMarried = getMarriedExpression();

        System.out.println("John is Male? " + isMale.interpreter("John"));
        System.out.println("Julia is Married? " + isMarried.interpreter("Married Julia"));
    }
}
/**
* John is Male? true
  Julia is Married? true
*/
```