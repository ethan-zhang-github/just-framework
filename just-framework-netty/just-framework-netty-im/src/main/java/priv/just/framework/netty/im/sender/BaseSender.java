package priv.just.framework.netty.im.sender;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.im.data.ProtoMsg;
import priv.just.framework.netty.im.data.User;
import priv.just.framework.netty.im.session.ClientSession;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 15:15
 */
@Slf4j
public abstract class BaseSender {

    private User user;

    private ClientSession session;

    public void sendMsg(ProtoMsg.Message msg) {
        if (session == null || !isConnected()) {
            log.info("unconnected!");
            return;
        }
        Channel channel = session.getChannel();
        ChannelFuture f = channel.writeAndFlush(msg);
        f.addListener(future -> {
            if (future.isSuccess()) {
                sendSucceed(msg);
            } else {
                sendFailed(msg);
            }
        });
    }

    protected void sendSucceed(ProtoMsg.Message msg) {
        log.info("send succeed!");
    }

    protected void sendFailed(ProtoMsg.Message msg) {
        log.info("send failed");
    }

    private boolean isConnected() {
        return false;
    }

}
