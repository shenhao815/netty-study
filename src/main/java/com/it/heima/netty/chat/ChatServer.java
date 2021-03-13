package com.it.heima.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 聊天程序服务器端
 */
public class ChatServer {

    private int port; // 服务器端端口号

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {

            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 往pipeline链中添加一个解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 往pipeline链中添加一个编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 往pipeline链中添加自定义的handler（业务处理类）
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("Netty Chat Server 启动");
            ChannelFuture cf = b.bind(port).sync();

            cf.channel().closeFuture().sync();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
            System.out.println("Netty Chat Server关闭......");
        }
    }

    public static void main(String[] args) {
        new ChatServer(9999).run();
    }
}
