package com.ljw.base.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description: 实例化Bean 单例模式
 * @Author Created by junwei.liang on 2018/11/21 15:56
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    String name() default "";
}
