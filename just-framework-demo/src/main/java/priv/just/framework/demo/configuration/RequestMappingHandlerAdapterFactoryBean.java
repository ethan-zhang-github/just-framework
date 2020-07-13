package priv.just.framework.demo.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.LinkedList;
import java.util.List;

@Component
public class RequestMappingHandlerAdapterFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        /*List<HandlerMethodArgumentResolver> resolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        LinkedList<HandlerMethodArgumentResolver> newResolvers = new LinkedList<>(resolvers);
        newResolvers.removeIf(adapter -> adapter instanceof RequestParamMethodArgumentResolver);
        newResolvers.addFirst(new CustomRequestParamMethodArgumentResolver());
        requestMappingHandlerAdapter.setArgumentResolvers(resolvers);*/
    }

    public static class CustomRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver {
        public CustomRequestParamMethodArgumentResolver() {
            super(false);
        }
        @Override
        protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
            Object resolved = super.resolveName(name, parameter, request);
            if (resolved instanceof String) {
                String res = (String) resolved;
                return res.replaceAll("s", "");
            }
            return resolved;
        }
    }

}
