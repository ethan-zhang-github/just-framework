package priv.just.framework.netty.im.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 14:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private String password;

}
