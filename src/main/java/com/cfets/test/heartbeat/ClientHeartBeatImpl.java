package com.cfets.test.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientHeartBeatImpl implements KeepAliveMessageFactory {
    @Override
    public Object getRequest(IoSession session) {
        //客户端发送心跳，获取心跳的内容
        return "tic";
    }
    @Override
    public Object getResponse(IoSession arg0, Object arg1) {
        //服务端不发送心跳，客户端就不需要响应心跳，直接返回null
        return null;
    }
    @Override
    public boolean isRequest(IoSession arg0, Object arg1) {
        //服务端不会给客户端发送心跳包，因此不需要判断，直接返回false
        return false;
    }
    @Override
    public boolean isResponse(IoSession arg0, Object arg1) {
        //客户端关注心跳的响应，因此需要进行判断
        if(arg1.toString().equals("tac"))
            return true;
        return false;
    }
}
