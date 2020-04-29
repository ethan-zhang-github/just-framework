package priv.just.framework.netty.im.command;

import lombok.Getter;
import priv.just.framework.netty.im.enums.CommandType;

import java.util.Scanner;

import static priv.just.framework.netty.im.enums.CommandType.UNKNOWN;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 14:54
 */
@Getter
public class ClientCommandMenu implements BaseCommand {

    private CommandType commandType;

    @Override
    public void execute(Scanner scanner) {
        System.out.println("请输入命令类型（type）");
        while (true) {
            String type = scanner.next();
            if (CommandType.of(Integer.valueOf(type)) == UNKNOWN) {
                System.out.println("未知命令，请重新输入！");
            } else {
                this.commandType = CommandType.of(Integer.valueOf(type));
                break;
            }
        }
    }

}
