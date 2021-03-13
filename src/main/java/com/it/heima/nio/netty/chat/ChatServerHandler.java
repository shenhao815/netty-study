package com.it.heima.nio.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器端业务处理类
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new ArrayList<>();

    @Override  // 通道就绪
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.add(inChannel);
        System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1) + " 上线");
    }

    @Override  // 通道未就绪
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server:]"+inChannel.remoteAddress().toString().substring(1)+" 离线");
    }

    @Override  // 读取数据
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel inChannel = ctx.channel();
        for (Channel channel : channels) {
            if (channel != inChannel) {
                channel.writeAndFlush("[" + inChannel.remoteAddress().toString().substring(1) + "]" + "说：" + s + "\n");
            }
        }

    }

    @Override  // 发生异常
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
