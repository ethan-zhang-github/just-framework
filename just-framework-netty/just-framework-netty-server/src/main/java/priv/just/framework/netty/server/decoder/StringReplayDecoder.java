package priv.just.framework.netty.server.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-25 13:59
 */
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.Status> {

    private int len;

    private byte[] bytes;

    public StringReplayDecoder() {
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PARSE_1:
                len = in.readInt();
                bytes = new byte[len];
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                in.readBytes(bytes, 0, len);
                out.add(new String(bytes, StandardCharsets.UTF_8));
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }
    }

    enum Status {
        PARSE_1, PARSE_2;
    }

}
