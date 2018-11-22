package com.ljw.base.ioc.init;

import com.ljw.base.aop.proxy.TransactionProxy;
import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;
import com.ljw.base.ioc.annotation.Service;
import com.ljw.base.util.ClassUtil;
import com.ljw.base.util.CollectionUtil;
import com.ljw.base.util.SacnUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/21 16:05
 */
public class InitRun {

    private InitRun() {
    }

    private void initClass() throws Exception {
        List<Class<?>> classes = SacnUtil.getClassListByAnnotation("", Bean.class, true);
        for (Class cla : classes) {
            Bean bean = (Bean) cla.getAnnotation(Bean.class);
            if (bean == null) {
                continue;
            }
            String beanName = bean.name();
            if (CollectionUtil.isNullOrEmpty(beanName)) {
                beanName = cla.getName();
            }
            if (BeanContainer.BEAN_MAP.containsKey(beanName)) {
                throw new Exception("存在重复的beanName:" + beanName);
            }
            BeanContainer.BEAN_MAP.put(beanName, cla.newInstance());
        }
    }

    private void initServiceClass() throws Exception {
        List<Class<?>> serviceClasses = SacnUtil.getClassListByAnnotation("", Service.class, true);
        for (Class cla : serviceClasses) {
            Service service = (Service) cla.getAnnotation(Service.class);
            if (service == null) {
                continue;
            }
            String beanName = service.name();
            if (CollectionUtil.isNullOrEmpty(beanName)) {
                beanName = cla.getName();
            }
            if (BeanContainer.BEAN_MAP.containsKey(beanName)) {
                throw new Exception("存在重复的beanName:" + beanName);
            }
            BeanContainer.BEAN_MAP.put(beanName, TransactionProxy.proxyFor(cla.newInstance()));
        }
    }

    private void initField() throws Exception {
        Map<String, Object> currMap = BeanContainer.BEAN_MAP;
        for (String key : currMap.keySet()) {
            Object obj = currMap.get(key);
            if (obj.getClass().getAnnotation(Bean.class) == null) {
                obj = ClassUtil.getTarget(obj);
            }
            List<Field> fields = ClassUtil.getAllFiledClass(obj.getClass());
            if (CollectionUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                Autowired resource = field.getAnnotation(Autowired.class);
                if (resource == null) {
                    continue;
                }
                String beanName = resource.beanName();
                if (CollectionUtil.isNullOrEmpty(beanName)) {
                    beanName = field.getType().getName();
                }
                Object writeValue = null;
                field.setAccessible(true);
                if (BeanContainer.BEAN_MAP.containsKey(beanName)) {
                    writeValue = BeanContainer.BEAN_MAP.get(beanName);
                } else if (field.getType().toString().contains("interface ")) {
                    List<Class<?>> allClassByInterface = ClassUtil.getAllClassByInterface(field.getType());
                    for (Class<?> clazz : allClassByInterface) {
                        if (BeanContainer.BEAN_MAP.containsKey(clazz.getName())) {
                            writeValue = BeanContainer.BEAN_MAP.get(clazz.getName());
                            break;
                        }
                    }
                } else {
                    writeValue = field.getType().newInstance();
                    BeanContainer.BEAN_MAP.put(beanName, writeValue);
                }
                field.set(obj, writeValue);
            }
        }
    }

    public static void start() {
        if (!BeanContainer.BEAN_MAP.isEmpty()) {
            System.out.println("容器已经启动!");
            return;
        }
        System.out.println("IOC容器启动...");
        InitRun initRun = new InitRun();
        try {
            System.out.println("初始化Bean...");
            initRun.initClass();
            initRun.initServiceClass();
            System.out.println("依赖注入...");
            initRun.initField();
            System.out.println("依赖加载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("IOC容器启动失败!");
        }
    }
}
