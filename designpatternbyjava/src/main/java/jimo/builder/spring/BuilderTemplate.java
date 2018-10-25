package jimo.builder.spring;

/**
 * Builder模式的模板
 *
 * @author jimo
 * @date 2018/10/25 9:47
 */
public class BuilderTemplate {

	private Object param1;
	private Object param2;
	// params...

	/**
	 * 构造函数,一般为private,也可能根据需要为public,取决于需求
	 *
	 * @author jimo
	 * @date 2018/10/25 9:48
	 */
	private BuilderTemplate(Object param1, Object param2) {
		this.param1 = param1;
		this.param2 = param2;
	}

	public static Builder builder(Object... params) {
		return new BuilderImpl();
	}

	private static class BuilderImpl implements Builder {
		Object param1;
		Object param2;
		// ...

		@Override
		public Builder method1(Object param1) {
			this.param1 = param1;
			return this;
		}

		@Override
		public Builder method2(Object param2) {
			this.param2 = param2;
			return this;
		}

		@Override
		public BuilderTemplate build() {
			// 也可能复杂一点的构造过程,比如有顺序之类的
			return new BuilderTemplate(param1, param2);
		}
	}

	// other methods

	public interface Builder {
		Builder method1(Object param1);

		Builder method2(Object param2);
		//...

		BuilderTemplate build();
	}

	public static void main(String[] args) {
		final BuilderTemplate template = BuilderTemplate.builder()
				.method1("1")
				.method2("2")
				.build();
		// 调用template对象的其他方法
	}
}
