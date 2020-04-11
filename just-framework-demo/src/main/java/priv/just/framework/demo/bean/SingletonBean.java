package priv.just.framework.demo.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import priv.just.framework.demo.service.DemoService;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-09 16:07
 */
@Component
public class SingletonBean implements FactoryBean<SingletonBean> {

    @Resource
    private DemoService demoService;

    public static final SingletonBean INSTANCE = new SingletonBean();

    private SingletonBean() {}

    @Override
    public SingletonBean getObject() throws Exception {
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return SingletonBean.class;
    }

    public void test() {
        demoService.test();
    }

}
