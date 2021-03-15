package com.it.heima.netty.rpc.serverstub;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

// 网络处理服务器
public class NettyRPCServer {

    private int port;

    public NettyRPCServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(port).childHandler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            // 解码器
                            // 采用Netty自带的ObjectEncoder和ObjectDecoder作为编码器（为了降低复杂度，这里没有采用第三方的编解码器）
                            // 当然实际开发时也可以采用JSON或XML
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            // 服务器端业务处理类
                            pipeline.addLast(new InvokeHandler());
                        }
                    }
            );
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println(".....Server is ready .......");

            future.channel().closeFuture().sync();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyRPCServer(9999).start();
    }
}
