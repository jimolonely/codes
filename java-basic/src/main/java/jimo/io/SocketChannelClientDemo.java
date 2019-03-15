package jimo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author jimo
 * @date 19-3-15 上午11:43
 */
public class SocketChannelClientDemo {

    public static void main(String[] args) {
        client();
    }

    static void client() {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(false);

            channel.connect(new InetSocketAddress("localhost", 8088));
            if (channel.finishConnect()) {
                int i = 0;
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "这是第" + i++ + "条来自客户端的信息";
                    buf.clear();
                    buf.put(info.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()) {
                        System.out.println(buf);
                        channel.write(buf);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
