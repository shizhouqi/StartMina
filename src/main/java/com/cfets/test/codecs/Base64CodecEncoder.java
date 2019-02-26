package com.cfets.test.codecs;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import javax.xml.bind.DatatypeConverter;

public class Base64CodecEncoder implements ProtocolEncoder {
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        System.out.println("发送的数据，加密前的内容："+ o);
        String encode = DatatypeConverter.printBase64Binary(o.toString().getBytes());
        byte bt[] = encode.getBytes();
        IoBuffer ioBuffer = IoBuffer.allocate(bt.length);
        ioBuffer.put(bt,0,bt.length);
        protocolEncoderOutput.write(ioBuffer.flip());
        System.out.println("发送的数据，加密后的内容："+ encode);
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
