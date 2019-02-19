package jimo.algo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 超哥开了一个造仿真机器人的公司，最近新出了一个造机器人眼球的测试机，但是造出来的眼球大小不一，你知道造眼球的原料很贵的，为了不浪费，
 * 他想将这些眼球合并成2个最大的眼球，当然不是为了摸的，是用来做广告宣传。
 * 问题来了，2个眼球必须一样大，且原来的单个小眼球不可分割，如果能够造成功，返回最大眼球的size，否则返回0。
 * 例如：
 * 输入小眼球数组(eyeball[n])：[1,1,2,3,6]，输出6（1，2，3合并为6,余下1）；
 * 输入：[1,2]，返回0，不可造。
 * <p>
 * 请大家帮助超哥吧^_^。
 * <p>
 * 数据限制：
 * 1. 1<= n <=20
 * 2. 1<= eyeball[i] <=1000 (0<=i<n)
 * 3. size<=5000(即眼球最大为5000)
 * </p>
 *
 * @author jimo
 * @date 18-12-14 上午8:28
 */
public class BigEyeball {

	int bigEyeball(Integer[] eyes) {
		int maxSize = 0;
		List<Integer> eyeList = Arrays.asList(eyes);
		eyeList.sort(Collections.reverseOrder());
		for (int i = 1; i <= eyes.length - 1; i++) {
			maxSize = Math.max(maxSize, getMax(i, eyeList));
		}
		return maxSize;
	}

	/**
	 * 求在只有一个眼球为最大时的情况
	 */
	private int getMax(int i, List<Integer> eyes) {
		for (int j = 0; j < eyes.size(); j++) {
			if (foundEqual(eyes.get(j), eyes.subList(j + 1, eyes.size()))) {
				return eyes.get(j);
			}
		}
		return 0;
	}

	/**
	 * 判断x是否可以由list中的数字不重复相加得到
	 * <p>
	 * x = a + list
	 *          |
	 *          a1 + list1
	 *                  |
	 *                  a2 + list2
	 *                          ...
	 *                          an + y
	 * </p>
	 */
	private boolean foundEqual(Integer x, List<Integer> list) {
		if (list.size() == 1) {
			return x == (int) list.get(0);
		}
		for (int i = 0; i < list.size(); i++) {
			list.remove(i);
			boolean ok = foundEqual(x - list.get(i), list);
			if (ok) {
				return true;
			}
		}
		return false;
	}
}
