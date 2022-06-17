package com.wch.study.netty.codec;

import com.alibaba.druid.support.json.JSONUtils;
import com.wch.study.netty.entity.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/16 11:21
 */
public class NettyEncoder extends MessageToByteEncoder<User> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(user.getId().getBytes());
        byteBuf.writeBytes(user.getUsername().getBytes());
        byteBuf.writeBytes(user.getPassword().getBytes());
    }
}
