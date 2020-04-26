package priv.just.framework.netty.server.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-25 11:05
 */
@Slf4j
public class ByteToIntegerReplayDecoder extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Integer res = in.readInt();
        log.info("解码出一个整数：【{}】", res);
        out.add(res);
    }

}
