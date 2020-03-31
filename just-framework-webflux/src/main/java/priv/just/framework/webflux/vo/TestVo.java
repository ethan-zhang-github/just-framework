package priv.just.framework.webflux.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-31 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVo {

    private String name;

    private Integer age;

    private Map<String ,String> attrs;

}
