package com.ljw.base.aop.proxy;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/22 16:30
 */
public class TransactionProxy {

    public static Object proxyFor(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new TransactionInvocationHandler(object));
    }
}