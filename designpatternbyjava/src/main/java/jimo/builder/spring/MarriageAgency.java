package jimo.builder.spring;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 婚姻中介所
 *
 * @author jimo
 * @date 2018/10/25 10:13
 */
public class MarriageAgency {
	private Description desc;

	public MarriageAgency(Description desc) {
		this.desc = desc;
	}

	/**
	 * @author jimo
	 * @date 2018/10/25 10:42
	 */
	public static Builder builder() {
		return new BuilderImpl();
	}

	/**
	 * 从数据库检索符合条件的对象
	 *
	 * @param client 想找对象的人
	 * @author jimo
	 * @date 2018/10/25 10:17
	 */
	public List<String> findMate(Description client) {
		desc.sex = client.sex == 0 ? 1 : 0;
		return new ArrayList<>();
	}

	/**
	 * 对象描述信息
	 *
	 * @author jimo
	 * @date 2018/10/25 10:36
	 */
	public static class Description {
		int height;
		Age age;
		int income;
		int beautiness;

		/**
		 * 0:男性,1:女性
		 */
		int sex;
	}

	private static class Age {
		int low;
		int high;

		public Age(int low, int high) {
			this.low = low;
			this.high = high;
		}
	}

	private static class BuilderImpl implements Builder {

		Description desc;

		@Override
		public Builder height(int height) {
			Assert.isTrue(height > 10 && height < 300, "这不是合法的人类高度");
			this.desc.height = height;
			return this;
		}

		@Override
		public Builder ageBetween(int low, int high) {
			this.desc.age = new Age(low, high);
			return this;
		}

		@Override
		public Builder income(int low) {
			this.desc.income = low;
			return this;
		}

		@Override
		public Builder beautiness(int value) {
			this.desc.beautiness = value;
			return this;
		}

		@Override
		public MarriageAgency build() {
			return null;
		}
	}

	public interface Builder {
		Builder height(int height);

		Builder ageBetween(int low, int high);

		Builder income(int low);

		Builder beautiness(int value);

		MarriageAgency build();
	}

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		final MarriageAgency agency = MarriageAgency.builder()
				.ageBetween(20, 30)
				.beautiness(9)
				.height(160)
				.income(10000)
				.build();
		agency.findMate(new Description());
	}
}
