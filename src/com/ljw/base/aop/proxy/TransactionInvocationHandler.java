package com.ljw.base.aop.proxy;

import com.ljw.base.aop.annotation.Transactional;
import com.ljw.base.util.DbHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/22 16:28
 */
public class TransactionInvocationHandler implements InvocationHandler {
    private Object target;

    TransactionInvocationHandler(Object object) {
        this.target = object;
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] objects) throws Throwable {
        Transactional annotation = method.getAnnotation(Transactional.class);
        if (annotation == null) {
            return method.invoke(target, objects);
        }
        DbHelper.beginTransaction();
        Object result = null;
        try {
            // 调用业务方法
            result = method.invoke(target, objects);
            DbHelper.commit();
        } catch (Exception e) {
            DbHelper.rollback();
        } finally {
            DbHelper.closeForTransaction();
        }
        return result;
    }

}