package com.cfets.test;
import com.cfets.test.codecs.Base64CodecFactory;
import com.cfets.test.heartbeat.ServerHeartBeatImpl;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MinaServer {
    public static void main(String [] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        IoFilter filter = new ProtocolCodecFilter(new Base64CodecFactory());
        acceptor.getFilterChain().addLast("code", filter);
        //------在拦截器链路中插入心跳机制开始------
        ServerHeartBeatImpl serverHeartBeat = new ServerHeartBeatImpl();
        //实例化一个KeepAliveFilter过滤器，传入serverHeartBeat引用，
        // IdleStatus参数为BOTH_IDLE表示如果当前连接的读写通道都空闲时，
        // 在指定的时间间隔getRequestInterval后发送出发Idle事件。
        KeepAliveFilter keepAliveFilter = new KeepAliveFilter(serverHeartBeat, IdleStatus.BOTH_IDLE);
        //使用了 KeepAliveFilter之后，IoHandlerAdapter中的 sessionIdle方法默认是不会再被调用的
        //设置为true，当session进入idle状态的时候 依然调用handler中的idled方法
        keepAliveFilter.setForwardEvent(true);
        //需要每10秒接受一个心跳请求，否则该连接进入空闲状态，并且发出idled方法回调
        keepAliveFilter.setRequestInterval(10);
        acceptor.getFilterChain().addLast("heart",keepAliveFilter);
        //------在拦截器链路中插入心跳机制结束------
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(9999));
    }
}
