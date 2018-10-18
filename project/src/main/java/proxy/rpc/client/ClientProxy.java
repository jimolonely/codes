package proxy.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理客户端请求，返回客户端需要的对象
 *
 * @author jimo
 * @date 2018/10/18 16:20
 */
public class ClientProxy {
	private static ClientInvoker invoker = new ClientInvoker();

	public static Object getObject(Class<?> clazz) {
		return  Proxy.newProxyInstance(
				clazz.getClassLoader(),
				new Class<?>[]{clazz},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// 模拟参数
						final Object params = new Object();

						return invoker.invoke(params, "localhost", 8088);
					}
				});
	}
}
