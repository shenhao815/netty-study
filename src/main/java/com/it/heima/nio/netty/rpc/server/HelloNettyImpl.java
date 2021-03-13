package com.it.heima.nio.netty.rpc.server;

public class HelloNettyImpl implements HelloNetty {
    @Override
    public String hello() {
        return "hello,netty";
    }
}
