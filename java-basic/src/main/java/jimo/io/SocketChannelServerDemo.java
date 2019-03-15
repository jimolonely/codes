package jimo.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author jimo
 * @date 19-3-15 上午11:43
 */
public class SocketChannelServerDemo {

    public static void main(String[] args) {
        server();
    }

    static void server() {
        try (ServerSocket socket = new ServerSocket(8088);
        ) {
            int recvMsgSize = 0;
            byte[] buf = new byte[1024];
            while (true) {
                Socket accept = socket.accept();
                SocketAddress clientAddr = accept.getRemoteSocketAddress();
                System.out.println("处理来自客户端[" + clientAddr + "]的请求");
                InputStream in = accept.getInputStream();
                while ((recvMsgSize = in.read(buf)) != -1) {
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(buf, 0, temp, 0, recvMsgSize);
                    System.out.println(new String(temp));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
