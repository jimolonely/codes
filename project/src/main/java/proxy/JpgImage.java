package proxy;

/**
 * jpg图片
 *
 * @author jimo
 * @date 2018/10/17 14:44
 */
public class JpgImage implements Image {
	@Override
	public void display(String name) {
		System.out.println("展示jpg图片：" + name);
	}
}
