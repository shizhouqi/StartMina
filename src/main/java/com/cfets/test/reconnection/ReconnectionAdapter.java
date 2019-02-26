package com.cfets.test.reconnection;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

public class ReconnectionAdapter extends IoFilterAdapter {
    private IoConnector connector;
    public ReconnectionAdapter(IoConnector connector){
        this.connector = connector;
    }
    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {
        while(true){
            try {
                Thread.sleep(3000);
                ConnectFuture future = connector.connect();
                future.awaitUninterruptibly();
                IoSession session  = future.getSession();
                if(session.isConnected()){
                    System.out.println("断线重连["+ connector.getDefaultRemoteAddress().toString() +"]成功");
                    break;
                }
            }catch (Exception e){
                System.out.println("重连服务器登录失败,3秒再连接一次:" + e.getMessage());
            }
        }
    }
}
