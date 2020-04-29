package priv.just.framework.netty.im.controller;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.im.session.ClientSession;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 15:59
 */
@Slf4j
public class CommandController {

    private boolean connectedFlag;

    GenericFutureListener<ChannelFuture> connectedListener = f -> {
        EventLoop eventLoop = f.channel().eventLoop();
        if (f.isSuccess()) {
            connectedFlag = true;
            log.info("connect to server success!");
            Channel channel = f.channel();
            ClientSession session = new ClientSession(channel);
            channel.closeFuture().addListener(ChannelFutureListener.CLOSE);
            notifyCommandThread();
        } else {
            log.info("connect failed, retry in 10 seconds!");
            eventLoop.schedule(() -> {}, 10, TimeUnit.SECONDS);
            connectedFlag = false;
        }
    };

    private void notifyCommandThread() {}

}
