package priv.just.framework.netty.server.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-20 14:18
 */
@Slf4j
public class NettyEchoServer {

    private int port;

    public NettyEchoServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup boosLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b
                .group(boosLoopGroup, workLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyEchoServerHandler());
                    }
                });

        try {
            ChannelFuture f = b.bind();
            f.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("服务端绑定成功");
                } else {
                    log.info("服务端绑定失败");
                }
            });
            f.sync();
            Channel channel = f.channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty echo server start error ...", e);
        } finally {
            workLoopGroup.shutdownGracefully();
            boosLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyEchoServer server = new NettyEchoServer(8090);
        server.run();
    }

}
