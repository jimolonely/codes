package proxy;

/**
 * png图片
 *
 * @author jimo
 * @date 2018/10/17 14:28
 */
public class PngImage implements Image {
	@Override
	public void display(String name) {
		System.out.println("展示png图片：" + name);
	}
}
