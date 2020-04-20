package priv.just.framework.netty.client.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-20 15:14
 */
@Slf4j
public class NettyEchoClient {

    private String host;

    private int port;

    public NettyEchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b
                    .group(workLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyEchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect();
            f.addListener(future -> {
               if (future.isSuccess()) {
                   log.info("客户端连接成功");
               } else {
                   log.info("客户端连接失败");
               }
            });
            f.sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            log.info("请输入发送内容：");
            while (scanner.hasNext()) {
                String next = scanner.next();
                byte[] bytes = (DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()) + ">>" + next).getBytes(StandardCharsets.UTF_8);
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
                log.info("请输入发送内容：");
            }
        } catch (Exception e) {
            log.error("netty echo client start error ...", e);
        } finally {
            workLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyEchoClient client = new NettyEchoClient("127.0.0.1", 8090);
        client.run();
    }

}
