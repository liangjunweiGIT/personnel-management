package com.ljw.base.ioc.init;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/21 16:03
 */
public class BeanContainer {
    static Map<String, Object> BEAN_MAP = new HashMap<>();

    public static Object getBean(Class cla) {
        String beanName = cla.getName();
        return BEAN_MAP.get(beanName);
    }

    public static Object getBean(String beanName) {
        return BEAN_MAP.get(beanName);
    }
}
