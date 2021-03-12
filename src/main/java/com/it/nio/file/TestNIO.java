package com.it.nio.file;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ch
 * @date 2021-3-12
 */

// 通过NIO实现文件IO
public class TestNIO {

    @Test  // 往本地文件中写数据
    public void test1() throws IOException {
        // 1、创建输出流
        FileOutputStream fos = new FileOutputStream("basic.txt");
        // 2、从流中得到一个通道
        FileChannel fc = fos.getChannel();
        // 3、提供一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4、往缓冲区中存入数据
        byte[] bytes = "hello,nio".getBytes();
        buffer.put(bytes);
        // 5、翻转缓冲区
        buffer.flip();
        // 6、把缓冲区写到通道中
        fc.write(buffer);
        // 7、关闭
        fos.close();// 只需关闭流就可以，因为通道是从流中获取的，只要把流关闭了，通道也就关闭了
    }
}
