package com.cfets.test.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class HeartBeatImpl implements KeepAliveMessageFactory {

    @Override
    public Object getRequest(IoSession session) {
        session.write("心跳");
        return null;
    }

    @Override
    public Object getResponse(IoSession arg0, Object arg1) {
        return null;
    }

    @Override
    public boolean isRequest(IoSession arg0, Object arg1) {
        return false;
    }

    @Override
    public boolean isResponse(IoSession arg0, Object arg1) {
        return false;
    }
}
