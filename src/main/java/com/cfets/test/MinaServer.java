package com.cfets.test;
import com.cfets.test.codecs.Base64CodecFactory;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MinaServer {
    public static void main(String [] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        IoFilter filter = new ProtocolCodecFilter(new Base64CodecFactory());
        acceptor.getFilterChain().addLast("code", filter);
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(9999));
    }
}
