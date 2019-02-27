package com.cfets.test.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ServerHeartBeatImpl implements KeepAliveMessageFactory {
    @Override
    public Object getRequest(IoSession session) {
        //被动型心跳处理，服务器不发送心跳，返回null
        return null;
    }

    @Override
    public Object getResponse(IoSession arg0, Object arg1) {
        //对于心跳包，进行反馈
        return "tac";
    }

    @Override
    public boolean isRequest(IoSession arg0, Object arg1) {
        //判断是否是心跳包
        if(arg1.toString().equals("tic"))
            return true;
        return false;
    }

    @Override
    public boolean isResponse(IoSession arg0, Object arg1) {
        //被动型心跳，服务器不主动发送心跳，就不关注反馈，直接返回false
        return false;
    }
}

