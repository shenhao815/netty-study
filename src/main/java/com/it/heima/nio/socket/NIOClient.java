package com.it.heima.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ch
 * @date 2021-3-12
 */

// 网络客户端程序
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 1、得到一个网络通道
        SocketChannel channel = SocketChannel.open();

        // 2、设置阻塞方式
        channel.configureBlocking(false);

        // 3、提供服务器端的ip和端口号
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);

        // 4、连接服务器端
        if (!channel.connect(address)) {
            while (!channel.finishConnect()) { // nio作为非阻塞式的优势
                System.out.println("Client:连接服务器端的同时，我还可以干别的事情");
            }
        }

        // 5、得到一个缓冲区，并存入数据
        String msg = "hello,Server";
        ByteBuffer writeBuf = ByteBuffer.wrap(msg.getBytes());

        // 6、发送数据
        channel.write(writeBuf);

        System.in.read();
    }
}
