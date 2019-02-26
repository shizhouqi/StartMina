package com.cfets.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("我是服务端，会话创建");
    }
    //当一个客户端连接进入时
    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("我是服务端，一个客户端连接:" + session.getRemoteAddress());
    }

    //当客户端发送消息到达时
    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println("我是服务端，收到客户端消息:" + message.toString());
        String str = "客户端IP：" + session.getRemoteAddress();
        session.write(str);
    }
    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("我是服务端，我发送的消息：" + message);
    }
    //当一个客户端连接关闭时
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("我是服务端，一个客户端连接断开");
    }

    //当发生异常时
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("我是服务端，服务器发生异常:" + cause);
    }
}