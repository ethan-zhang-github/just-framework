package priv.just.framework.netty.server.demo;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-19 9:46
 */
public class NettyServerStarter {

    public static void main(String[] args) throws InterruptedException {
        NettyServer server = new NettyServer(8070);
        server.start();
    }

}
