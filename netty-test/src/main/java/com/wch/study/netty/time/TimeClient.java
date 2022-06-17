package com.wch.study.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/20 9:00
 */

/**
 * Netty 中服务器和客户端之间最大也是唯一的区别是使用了不同的 Bootstrap 和 Channel 实现。请看下面的代码
 */
public class TimeClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            /**
             * Bootstrap 与 ServerBootstrap 类似，只是它用于非服务器通道，例如客户端或无连接通道。
             */
            Bootstrap b = new Bootstrap(); // (1)
            /**
             * 如果您只指定一个 EventLoopGroup，它将同时用作老板组和工作组。但是，老板工人不用于客户端。
             */
            b.group(workerGroup); // (2)
            /**
             * NioSocketChannel 不是 NioServerSocketChannel，而是用于创建客户端 Channel
             */
            b.channel(NioSocketChannel.class); // (3)
            /**
             * 请注意，这里不像我们使用 ServerBootstrap 那样使用 childOption()，
             * 因为客户端 SocketChannel 没有父级
             */
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
