package com.wch.study.netty.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/19 17:16
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /**
         * NioEventLoopGroup 是一个处理 I/O 操作的多线程事件循环。
         * Netty 为不同类型的传输提供了各种 EventLoopGroup 实现。
         * 在这个例子中，我们正在实现一个服务器端应用程序，因此将使用两个 NioEventLoopGroup。第一个，通常称为“boss”，接受传入连接。第二个，通常称为“worker”，
         * 一旦老板接受连接并将接受的连接注册到工作人员，就会处理接受的连接的流量。
         * 使用多少线程以及它们如何映射到创建的通道取决于 EventLoopGroup 实现，甚至可以通过构造函数进行配置。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * ServerBootstrap 是一个设置服务器的辅助类。您可以直接使用 Channel 设置服务器。但是，请注意，这是一个乏味的过程，在大多数情况下您不需要这样做
             */
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    /**
                     * 我们指定使用 NioServerSocketChannel 类，该类用于实例化一个新的 Channel 来接受传入的连接
                     */
                    .channel(NioServerSocketChannel.class) // (3)
                    /**
                     * 此处指定的处理程序将始终由新接受的 Channel 评估。
                     * ChannelInitializer 是一个特殊的处理程序，旨在帮助用户配置新的 Channel。
                     * 您很可能希望通过添加一些处理程序（例如 DiscardServerHandler）来配置新 Channel 的 ChannelPipeline 来实现您的网络应用程序。
                     * 随着应用程序变得复杂，您可能会向管道添加更多处理程序，并最终将这个匿名类提取到顶级类中
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /**
                     *您还可以设置特定于 Channel 实现的参数。
                     * 我们正在编写一个 TCP/IP 服务器，因此我们可以设置套接字选项，例如 tcpNoDelay 和 keepAlive。
                     * 请参阅 ChannelOption 的 apidocs 和具体的 ChannelConfig 实现，以了解支持的 ChannelOptions 的概述。
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    /**
                     * 你注意到 option() 和 childOption() 了吗？ option() 用于接受传入连接的 NioServerSocketChannel。
                     * childOption() 用于父 ServerChannel 接受的 Channels，在本例中为 NioSocketChannel。
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
