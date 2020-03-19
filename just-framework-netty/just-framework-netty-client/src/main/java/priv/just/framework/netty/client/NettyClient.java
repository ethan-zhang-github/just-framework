package priv.just.framework.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-19 9:53
 */
@Slf4j
public class NettyClient {

    private String host;

    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port)).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(nettyClientHandler);
                }
            });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty client start error", e);
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
