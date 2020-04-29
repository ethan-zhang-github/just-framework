package priv.just.framework.netty.im.client;

import lombok.extern.slf4j.Slf4j;
import priv.just.framework.netty.im.command.BaseCommand;
import priv.just.framework.netty.im.command.ClientCommandMenu;
import priv.just.framework.netty.im.command.LoginConsoleCommand;
import priv.just.framework.netty.im.data.User;
import priv.just.framework.netty.im.enums.CommandType;
import priv.just.framework.netty.im.session.ClientSession;

import java.util.Scanner;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 14:44
 */
@Slf4j
public class CommandClient {

    private boolean connectFlag;

    private ClientSession session;

    public void startCommandThread() throws IllegalAccessException, InstantiationException {
        while (true) {
            // 建立连接
            while (!connectFlag) {
                // 开始连接
                startConnectServer();
                waitCommandThread();
            }
            // 处理命令
            while (session != null && session.isConnected()) {
                Scanner scanner = new Scanner(System.in);
                ClientCommandMenu clientCommandMenu = new ClientCommandMenu();
                clientCommandMenu.execute(scanner);
                CommandType commandType = clientCommandMenu.getCommandType();
                Class<? extends BaseCommand> commandClazz = commandType.getCommand();
                BaseCommand command = commandClazz.newInstance();
                switch (commandType) {
                    case LOGIN:

                }
            }
        }
    }

    private void startConnectServer() {}

    private void waitCommandThread() {}

    private void startLogin(LoginConsoleCommand command) {
        if (!connectFlag) {
            log.info("connect error, please connect again!");
        }
        User user = new User(command.getUsername(), command.getPassword());

    }

}
