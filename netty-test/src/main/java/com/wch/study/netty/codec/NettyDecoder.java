package com.wch.study.netty.codec;

import com.wch.study.netty.entity.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/16 13:34
 */
public class NettyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println(byteBuf.toString(Charset.defaultCharset()));
    }
}
