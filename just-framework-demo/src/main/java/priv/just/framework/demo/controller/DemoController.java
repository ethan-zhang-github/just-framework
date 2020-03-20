package priv.just.framework.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.demo.service.DemoService;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-31 17:37
 */
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController {

    @Resource
    private BeanFactory beanFactory;
    
    @Resource
    private ApplicationContext applicationContext;

    @PostMapping("test")
    public void test() {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        System.out.println(beanFactory == autowireCapableBeanFactory);
        BeanFactory internalBeanFactory = beanFactory.getBean(BeanFactory.class);
        System.out.println(internalBeanFactory);
    }

    @GetMapping("test2")
    public void test2() {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
            String[] beanNamesForType = listableBeanFactory.getBeanNamesForType(DemoService.class);
            System.out.println(Arrays.toString(beanNamesForType));
        }
    }

}
