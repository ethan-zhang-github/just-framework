package priv.just.framework.netty.client.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.client.encoder.JsonMsgEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-27 18:00
 */
@Slf4j
public class JsonClient {

    private static final String CONTENT = "Hello, My name is JUST1984!";

    private String host;

    private int port;

    private Bootstrap b = new Bootstrap();

    public JsonClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            b.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port).option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldPrepender(4));
                            ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
                            ch.pipeline().addLast(new JsonMsgEncoder());
                        }
                    });
            ChannelFuture connectFuture = b.connect();
            connectFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Json client bind server host {} port {} successfully", host, port);
                } else {
                    log.error("Json client bind server host {} port {} error", host, port);
                }
            });
            connectFuture.sync();
            Channel channel = connectFuture.channel();
            for (int i = 0; i < 100; i++) {
                channel.writeAndFlush(new JsonMsg(i, CONTENT));
            }
            channel.flush();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("Json client start error", e);
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JsonMsg {

        private int index;

        private String content;

    }

    public static void main(String[] args) {
        new JsonClient("127.0.0.1", 8080).start();
    }

}
