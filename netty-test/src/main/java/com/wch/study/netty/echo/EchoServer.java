package com.wch.study.netty.echo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wch.study.netty.codec.NettyDecoder;
import com.wch.study.netty.codec.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/15 14:48
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                            " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);        //1
        new EchoServer(port).start();                //2
    }

    public void start() throws InterruptedException {
        ThreadFactory bossThreadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("NettyServerBossThread_%s").build();
        ThreadFactory workerThreadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("NettyServerWorkerThread_%s").build();
        NioEventLoopGroup bossGroup=new NioEventLoopGroup(1,bossThreadFactory);
        NioEventLoopGroup workerGroup=new NioEventLoopGroup(1,workerThreadFactory);
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)  {
                            ch.pipeline().addLast(new NettyDecoder())
                                    .addLast(new NettyEncoder())
                                    .addLast(new EchoServerHandler())
                                    .addLast(new EchoServerHandler3())
                                    .addLast(new EchoServerHandler2())
                                    .addLast(new EchoServerHandler4());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }

    }


}
