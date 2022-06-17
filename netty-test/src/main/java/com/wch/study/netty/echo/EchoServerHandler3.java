package com.wch.study.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/15 14:37
 */
@ChannelHandler.Sharable
public class EchoServerHandler3 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("响应处理器1");
        ByteBuf byteMsg = (ByteBuf) msg;
        byteMsg.writeBytes("增加请求1的内容".getBytes(Charset.forName("utf-8")));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
