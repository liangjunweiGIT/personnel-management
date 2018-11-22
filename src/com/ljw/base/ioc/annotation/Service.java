package com.ljw.base.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description: 管理Service层 每个业务方法开启一个事务
 * @Author Created by junwei.liang on 2018/11/21 16:50
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String name() default "";
}
