package priv.just.framework.netty.server.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-18 18:35
 */
@Slf4j
public class NettyServer {

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        NettyServerHandler nettyServerHandler = new NettyServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(nettyServerHandler);
                }
            });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty server start error", e);
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
