package priv.just.framework.zookeeper.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 15:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    @AliasFor("lockPath")
    String value();

    @AliasFor("value")
    String lockPath();

    long time() default 60;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
