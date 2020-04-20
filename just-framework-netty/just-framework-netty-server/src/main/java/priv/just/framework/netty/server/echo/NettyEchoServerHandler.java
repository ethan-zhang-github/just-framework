package priv.just.framework.netty.server.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-20 14:22
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("msg type：【{}】", in.hasArray() ? "heap" : "direct");
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        String receive = new String(arr, StandardCharsets.UTF_8);
        log.info("server receive：【{}】", receive);
        log.info("before response reference count：【{}】", ((ByteBuf) msg).refCnt());
        ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener((ChannelFutureListener) future -> {
            log.info("after response reference count：【{}】", ((ByteBuf) msg).refCnt());
        });
    }

}
