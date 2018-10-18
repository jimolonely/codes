package proxy.rpc.client;

import proxy.rpc.HelloService;

/**
 * 如何使用
 *
 * @author jimo
 * @date 2018/10/18 16:27
 */
public class Main {
	public static void main(String[] args) {
		final HelloService helloService = (HelloService) ClientProxy.getObject(HelloService.class);
		helloService.sayHello();
	}
}
