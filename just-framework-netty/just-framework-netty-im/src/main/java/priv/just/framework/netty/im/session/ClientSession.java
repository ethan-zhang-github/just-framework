package priv.just.framework.netty.im.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.im.data.ProtoMsg;
import priv.just.framework.netty.im.data.User;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 15:21
 */
@Slf4j
@Getter
@Setter
public class ClientSession {

    public static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private Channel channel;

    private User user;

    private String sessionId;

    private boolean isConnected;

    private boolean isLogin;

    public ClientSession(Channel channel) {
        this.channel = channel;
        this.sessionId = String.valueOf(-1);
        channel.attr(SESSION_KEY).set(this);
    }

    public static void loginSuccess(ChannelHandlerContext ctx, ProtoMsg.Message msg) {
        ClientSession session = getSession(ctx);
        session.setSessionId(msg.getSessionId());
        session.setLogin(true);
        log.info("login success!");
    }

    public static ClientSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(SESSION_KEY).get();
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    public ChannelFuture writeAndFlush(Object msg) {
        return channel.writeAndFlush(msg);
    }

    public void writeAndClose(Object msg) {
        writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
    }

    public void close() {
        isConnected = false;
        ChannelFuture f = channel.close();
        f.addListener(future -> {
            if (future.isSuccess()) {
                log.info("disconnected!");
            }
        });
    }

}
