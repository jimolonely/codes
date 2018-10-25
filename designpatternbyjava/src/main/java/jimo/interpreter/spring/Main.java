package jimo.interpreter.spring;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author jimo
 * @date 2018/10/24 17:49
 */
public class Main {

	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		final Expression expression = parser.parseExpression("'hello,jimo'");
		final Object msg = expression.getValue();
		System.out.println(msg);

		final Expression expression1 = parser.parseExpression("'hello,jimo'.length()");
		System.out.println(expression1.getValue());

		final Expression expression3 = parser.parseExpression("'hello,jimo'.concat(',yes')");
		System.out.println(expression3.getValue());

		final Person person = new Person();
		final EvaluationContext evaluationContext = new StandardEvaluationContext(person);
		evaluationContext.setVariable("myName", "jimo");
		final Expression expression2 = parser.parseExpression("name = #myName");
		System.out.println(expression2.getValue(evaluationContext));
		System.out.println(person.name);
	}

	/**
	 * 必须是public的且可读的变量
	 *
	 * @author jimo
	 * @date 2018/10/24 18:48
	 */
	public static class Person {
		public String name;
	}
}
