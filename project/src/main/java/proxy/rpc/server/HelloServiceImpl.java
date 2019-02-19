package proxy.rpc.server;

import proxy.rpc.HelloService;

/**
 * @author jimo
 * @date 2018/10/18 16:11
 */
public class HelloServiceImpl implements HelloService {
	@Override
	public void sayHello() {
		System.out.println("hello");
	}
}
