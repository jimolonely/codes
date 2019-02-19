package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jimo
 * @date 2018/10/17 15:06
 */
public class DynamicImageProxy {

	public static void display(String name, Image image) {
		final Image proxiedImage = (Image) Proxy.newProxyInstance(Image.class.getClassLoader(),
				new Class[]{Image.class},
				new ImageProxyHandler(image));
		proxiedImage.display(name);
	}

	private static class ImageProxyHandler implements InvocationHandler {

		private Object image;

		ImageProxyHandler(Object image) {
			this.image = image;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("调整下图片大小,美化一下...");
			final Object invoke = method.invoke(image, args);
			System.out.println("图片资源清理...");
			return invoke;
		}
	}
}
