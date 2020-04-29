package priv.just.framework.netty.im.data;

import lombok.Getter;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 15:23
 */
public class ProtoMsg {

    @Getter
    public static class Message {

        private String sessionId;

    }

}
