package priv.just.framework.demo.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import priv.just.framework.demo.annotation.ApiVersion;

import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerMethodArgumentResolverDecorator implements HandlerMethodArgumentResolver {

    private final HandlerMethodArgumentResolver delegate;

    public HandlerMethodArgumentResolverDecorator(HandlerMethodArgumentResolver delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return delegate.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object res = delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        Method targetMethod = parameter.getMethod();
        if (Objects.isNull(res) || Objects.isNull(targetMethod) || !targetMethod.isAnnotationPresent(ApiVersion.class)) {
            return res;
        }
        ApiVersion apiVersion = targetMethod.getAnnotation(ApiVersion.class);
        Method setVersionMethod = ReflectionUtils.findMethod(res.getClass(), "setVersion", int.class);
        if (Objects.isNull(setVersionMethod)) {
            return res;
        }
        setVersionMethod.invoke(res, apiVersion.value());
        return res;
    }

}
