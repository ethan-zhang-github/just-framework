package priv.just.framework.netty.im.enums;

import lombok.Getter;
import priv.just.framework.netty.im.command.BaseCommand;
import priv.just.framework.netty.im.command.LoginConsoleCommand;

import java.util.Arrays;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 14:55
 */
@Getter
public enum CommandType {

    UNKNOWN(0, "未知"),
    LOGIN(1, "登录", LoginConsoleCommand.class);

    private int key;

    private String tip;

    private Class<? extends BaseCommand> command;

    CommandType(int key, String tip, Class<? extends BaseCommand> command) {
        this.key = key;
        this.tip = tip;
        this.command = command;
    }

    CommandType(int key, String tip) {
        this(key, tip, null);
    }

    public static CommandType of(int key) {
        return Arrays.stream(values()).filter(i -> i.key == key).findFirst().orElse(UNKNOWN);
    }

}
