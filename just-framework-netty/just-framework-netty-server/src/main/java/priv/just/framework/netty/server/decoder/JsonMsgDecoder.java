package priv.just.framework.netty.server.decoder;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-27 17:16
 */
@Slf4j
public class JsonMsgDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String jsonStr = (String) msg;
        JSONObject res = JSONObject.parseObject(jsonStr);
        log.info("收到一个JSON数据包：【{}】", res);
    }

}
