package jimo.interpreter;

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
