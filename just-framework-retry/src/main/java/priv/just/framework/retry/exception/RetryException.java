package priv.just.framework.retry.exception;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-06 9:00
 */
public class RetryException extends RuntimeException {

    public RetryException(String msg) {
        super(msg);
    }

}
