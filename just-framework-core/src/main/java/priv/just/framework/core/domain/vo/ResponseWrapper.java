package priv.just.framework.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private static final Object EMPTY_DATA = new Object();

    private boolean seccess;

    private String code;

    private String msg;

    private T data;

    public static <T> ResponseWrapper<T> success(T data) {
        return new ResponseWrapper<>(true, "seccess", "成功", data);
    }

    public static ResponseWrapper<Object> success() {
        return new ResponseWrapper<>(true, "seccess", "成功", EMPTY_DATA);
    }

    public static ResponseWrapper<Object> failure(String code, String message) {
        return new ResponseWrapper<>(false, code, message, EMPTY_DATA);
    }

    public static ResponseWrapper<Object> failure(String message) {
        return failure("error", message);
    }

    public static ResponseWrapper<Object> authenticationError() {
        return failure("authenticationError", "登录授权失败");
    }

    public static ResponseWrapper<Object> unAuthenticated() {
        return failure("unAuthenticated", "未登录或无权访问该资源");
    }

    public static ResponseWrapper<Object> unAuthorised() {
        return failure("unAuthorised", "无权限访问该资源");
    }

}
