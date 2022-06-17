package com.wch.study.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/20 8:48
 */
public class TimeServerHandler  extends ChannelInboundHandlerAdapter {

    /**
     * 如前所述，channelActive() 方法将在连接建立并准备好生成流量时被调用。让我们在这个方法中写一个表示当前时间的 32 位整数
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        /**
         * 要发送新消息，我们需要分配一个包含该消息的新缓冲区。我们要写入一个 32 位整数，
         * 因此我们需要一个容量至少为 4 字节的 ByteBuf。通过 ChannelHandlerContext.alloc() 获取当前的 ByteBufAllocator 并分配一个新的缓冲区。
         */
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        /**
         * 那么当写请求完成时我们如何得到通知呢？这就像将 ChannelFutureListener 添加到返回的 ChannelFuture 一样简单。
         * 在这里，我们创建了一个新的匿名 ChannelFutureListener，它在操作完成时关闭 Channel。
         *
         * 或者，您可以使用预定义的侦听器简化代码
         */
        final ChannelFuture f = ctx.writeAndFlush(time);
//        f.addListener(ChannelFutureListener.CLOSE);
        // (3)
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
            ctx.close();
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
