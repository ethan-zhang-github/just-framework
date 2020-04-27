package priv.just.framework.netty.client.encoder;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import priv.just.framework.netty.client.demo.JsonClient;

import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-27 18:30
 */
public class JsonMsgEncoder extends MessageToMessageEncoder<JsonClient.JsonMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, JsonClient.JsonMsg msg, List<Object> out) throws Exception {
        out.add(JSONObject.toJSONString(msg));
    }

}
