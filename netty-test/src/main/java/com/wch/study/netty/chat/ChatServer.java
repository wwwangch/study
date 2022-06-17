package com.wch.study.netty.chat;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/20 10:27
 */
public class ChatServer {
    public void start() throws InterruptedException {
        ThreadFactory bossThreadFactory=new ThreadFactoryBuilder().setNameFormat("NettyServerBossThread_%s").build();
        ThreadFactory workerThreadFactory=new ThreadFactoryBuilder().setNameFormat("NettyWorkerBossThread_%s").build();
        EventLoopGroup bossGroup=new NioEventLoopGroup(1,bossThreadFactory);
        EventLoopGroup workerGroup=new NioEventLoopGroup(1,workerThreadFactory);

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChatServerHandler2())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChatServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                /**
                 * 你注意到 option() 和 childOption() 了吗？ option() 用于接受传入连接的 NioServerSocketChannel。
                 * childOption() 用于父 ServerChannel 接受的 Channels，在本例中为 NioSocketChannel。
                 */
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();

        // Wait until the server socket is closed.
        // In this example, this does not happen, but you can do that to gracefully
        // shut down your server.
        channelFuture.channel().closeFuture().sync();
    }
}
