package jimo.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jimo
 * @date 19-3-15 上午8:56
 */
public class ChannelDemo {

    public void streamRead(String filePath) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buf = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(buf)) != -1) {
                for (int i = 0; i < byteRead; i++) {
                    System.out.print((char) buf[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nioRead(String filePath) {
        try (RandomAccessFile af = new RandomAccessFile(filePath, "rw")) {
            FileChannel channel = af.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (channel.read(buf) != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.compact();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
