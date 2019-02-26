package com.cfets.test.codecs;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class Base64CodecFactory implements ProtocolCodecFactory {
    private Base64CodecEncoder encoder;
    private Base64CodecDecoder decoder;
    public Base64CodecFactory(){
        encoder = new Base64CodecEncoder();
        decoder = new Base64CodecDecoder();
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
