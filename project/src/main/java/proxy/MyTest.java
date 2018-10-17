package proxy;

import org.junit.Test;

public class MyTest {

	@Test
	public void testStaticProxy() {
		final PngImage pngImage = new PngImage();
		ImageProxy proxy = new ImageProxy(pngImage);
		proxy.display("jimo.png");

		final JpgImage jpgImage = new JpgImage();
		proxy = new ImageProxy(jpgImage);
		proxy.display("jimo.jpg");
	}

	@Test
	public void testDynamicProxy() {
		final PngImage pngImage = new PngImage();
		DynamicImageProxy.display("jimo.png", pngImage);

		DynamicImageProxy.display("jimo.jpg", new JpgImage());
	}
}
