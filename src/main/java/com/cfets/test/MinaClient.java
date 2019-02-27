package com.cfets.test;

import com.cfets.test.codecs.Base64CodecFactory;
import com.cfets.test.heartbeat.ClientHeartBeatImpl;
import com.cfets.test.reconnection.ReconnectionAdapter;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class MinaClient {
    public static void main(String[] args) throws InterruptedException {
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(3000);  //设置连接超时
        connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);  //读写都空闲时间:30秒
        connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 5);//读(接收通道)空闲时间:40秒
        connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 50);//写(发送通道)空闲时间:50秒
        //------拦截器处理链中加入心跳开始----------
        KeepAliveFilter keepAliveFilter = new KeepAliveFilter(new ClientHeartBeatImpl(), IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.CLOSE);//实例化一个 KeepAliveFilter 过滤器
        keepAliveFilter.setForwardEvent(true);// 继续调用IoHandlerAdapter中的sessionIdle事件
        keepAliveFilter.setRequestInterval(5);//设置当连接的读取通道空闲的时候，心跳包请求时间间隔
        keepAliveFilter.setRequestTimeout(10);//设置心跳包请求后 等待反馈超时时间。 超过该时间后则调用KeepAliveRequestTimeoutHandler.CLOSE
        connector.getFilterChain().addLast("heartbeat", keepAliveFilter);
        //-------拦截器处理链中加入心跳结束-----------
        //------拦截器处理链中加入超时/断线重连开始----------
        connector.getFilterChain().addLast("reconnection", new ReconnectionAdapter(connector));
        //------拦截器处理链中加入超时/断线重连结束----------
        IoFilter filter = new ProtocolCodecFilter(new Base64CodecFactory());
        connector.getFilterChain().addFirst("code",filter);
        connector.setHandler(new ClientHandler());
        connector.setDefaultRemoteAddress(new InetSocketAddress("127.0.0.1", 9999));
        ConnectFuture connectFuture = connector.connect();
        connectFuture.awaitUninterruptibly();
        if(!connectFuture.isConnected()){
            System.out.println("连接失败");
            return;
        }
        connectFuture.getSession().write("你好");
    }
}
