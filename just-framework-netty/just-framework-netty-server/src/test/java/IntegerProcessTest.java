import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import priv.just.framework.netty.server.decoder.ByteToIntegerReplayDecoder;
import priv.just.framework.netty.server.handler.IntegerProcessHandler;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-25 10:42
 */
public class IntegerProcessTest {

    @Test
    public void test() throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new ByteToIntegerReplayDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1984);
        channel.writeInbound(buffer);

        new CountDownLatch(1).await();
    }

}
