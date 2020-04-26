package priv.just.framework.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-25 14:22
 */
@Slf4j
public class StringProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String in = (String) msg;
        log.info("处理一个字符串：【{}】", in);
    }

}
