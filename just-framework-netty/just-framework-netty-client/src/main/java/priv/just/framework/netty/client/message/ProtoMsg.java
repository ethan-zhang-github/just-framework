package priv.just.framework.netty.client.message;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 13:48
 */
public class ProtoMsg {

    public static class Message {

        public byte[] toByteArray() {
            return JSONObject.toJSONString(this).getBytes();
        }

    }

}
