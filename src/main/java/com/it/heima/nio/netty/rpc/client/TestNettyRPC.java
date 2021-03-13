package com.it.heima.nio.netty.rpc.client;

import com.it.heima.nio.netty.rpc.clientStub.NettyPRCProxy;

// 服务调用方（消费方）
public class TestNettyRPC {

    public static void main(String[] args) {
        // 第1次远程调用
        HelloNetty helloNetty = (HelloNetty) NettyPRCProxy.create(HelloNetty.class);
        System.out.println(helloNetty.hello());

        // 第2次远程调用
        HelloRPC helloRPC = (HelloRPC) NettyPRCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("RPC"));

    }
}
