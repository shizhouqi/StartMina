package com.cfets.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.transport.socket.SocketSessionConfig;

public class ClientHandler extends IoHandlerAdapter {
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//       System.out.println("-客户端与服务端连接[空闲] - " + status.toString());
//        if(session != null){
//            session.closeNow();
//        }
    }
    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("我是客户端，会话创建");
        IoSessionConfig config = session.getConfig();
        if(config instanceof SocketSessionConfig)
            ((SocketSessionConfig) config).setKeepAlive(true);
    }
    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("我是客户端，连接服务端打开:" + session.getRemoteAddress());
        //session.write("你好");
    }
    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println("我是客户端，收到响应：" + message.toString());
    }
    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("我是客户端，我发送的消息：" + message);
    }
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("我是客户端，连接服务端的连接断开");
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("我是客户端，系统出现异常:" + cause);
    }
}
