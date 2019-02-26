package com.cfets.test.codecs;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import javax.xml.bind.DatatypeConverter;

public class Base64CodecDecoder implements ProtocolDecoder {
    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        byte bt[] = new byte[ioBuffer.limit()];
        ioBuffer.get(bt);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<bt.length;i++){
            sb.append((char)bt[i]);
        }
        String decode = sb.toString();
        System.out.println("收到的加密数据:"+decode);
        String code = new String(DatatypeConverter.parseBase64Binary(decode),"UTF-8");
        protocolDecoderOutput.write(code);
        System.out.println("收到的数据进行解密后的内容:"+code);
    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
