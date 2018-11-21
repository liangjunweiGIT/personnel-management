package com.ljw.base.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description: 依赖注入
 * @Author Created by junwei.liang on 2018/11/21 15:58
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String beanName() default "";
}
