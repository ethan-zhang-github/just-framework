package priv.just.framework.netty.im.command;

import lombok.Getter;

import java.util.Scanner;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 14:23
 */
@Getter
public class LoginConsoleCommand implements BaseCommand {

    private String username;

    private String password;

    @Override
    public void execute(Scanner scanner) {
        System.out.println("请输入用户信息（username:password）");
        String[] info = new String[2];
        while (true) {
            String input = scanner.next();
            String[] inputs = input.split(":");
            if (inputs.length < 2) {
                System.out.println("请输入正确格式！");
            } else {
                info[0] = inputs[0];
                info[1] = inputs[1];
                break;
            }
        }
        this.username = info[0];
        this.password = info[1];
    }

}
