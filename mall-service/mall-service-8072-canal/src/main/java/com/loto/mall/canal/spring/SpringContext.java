package com.loto.mall.canal.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-28 11:26<p>
 * PageName：SpringContext.java<p>
 * Function：
 */

@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext act) throws BeansException {
        applicationContext = act;
    }

    public static <T> T getBean(Class clazz) {
        return applicationContext.getBean((Class<T>) clazz);
    }
}
