package proxy;

/**
 * 图片静态代理
 *
 * @author jimo
 * @date 2018/10/17 14:30
 */
public class ImageProxy implements Image {
	private Image image;

	public ImageProxy(Image image) {
		this.image = image;
	}

	@Override
	public void display(String name) {
		System.out.println("调整下图片大小,美化一下...");
		image.display(name);
		System.out.println("图片资源清理...");
	}
}
