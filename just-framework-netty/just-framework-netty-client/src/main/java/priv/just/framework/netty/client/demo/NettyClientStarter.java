package priv.just.framework.netty.client.demo;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-19 10:12
 */
public class NettyClientStarter {

    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("localhost", 8070);
        client.start();
    }

}
