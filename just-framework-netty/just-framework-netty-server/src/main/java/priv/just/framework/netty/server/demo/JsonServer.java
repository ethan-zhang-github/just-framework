package priv.just.framework.netty.server.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.server.decoder.JsonMsgDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-27 17:30
 */
@Slf4j
public class JsonServer {

    private int port;

    public JsonServer(int port) {
        this.port = port;
    }

    private ServerBootstrap b = new ServerBootstrap();

    public void start() {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            b.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                            ch.pipeline().addLast(new JsonMsgDecoder());
                        }
                    });
            ChannelFuture bindFuture = b.bind(port);
            bindFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Json server bind port {} successfully", port);
                } else {
                    log.error("Json server bind port {} error", port);
                }
            });
            bindFuture.sync();
            Channel channel = bindFuture.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("Json server start error", e);
        } finally {
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new JsonServer(8080).start();
    }

}
