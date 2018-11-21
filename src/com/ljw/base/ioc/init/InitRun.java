package com.ljw.base.ioc.init;

import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;
import com.ljw.base.util.ClassUtil;
import com.ljw.base.util.CollectionUtil;
import com.ljw.base.util.SacnUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/21 16:05
 */
public class InitRun {
    public void initClass() throws Exception {
        List<Class<?>> clas = SacnUtil.getClassListByAnnotation("com", Bean.class, true);
        for (Class cla : clas) {
            Bean bean = (Bean) cla.getAnnotation(Bean.class);
            if (CollectionUtil.isNullOrEmpty(bean)) {
                continue;
            }
            String beanName = bean.beanName();
            if (CollectionUtil.isNullOrEmpty(beanName)) {
                beanName = cla.getName();
            }
            if (BeanContainer.BEAN_MAP.containsKey(beanName)) {
                throw new Exception("存在重复的beanName:" + beanName);
            }
            BeanContainer.BEAN_MAP.put(beanName, cla.newInstance());
        }
    }

    public void initField() throws Exception {
        Map<String, Object> currMap = BeanContainer.BEAN_MAP;
        for (String key : currMap.keySet()) {
            Object obj = currMap.get(key);
            Field[] fields = obj.getClass().getDeclaredFields();
            if (CollectionUtil.isNullOrEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                Autowired reource = field.getAnnotation(Autowired.class);
                if (CollectionUtil.isNullOrEmpty(reource)) {
                    continue;
                }
                String beanName = reource.beanName();
                if (CollectionUtil.isNullOrEmpty(beanName)) {
                    beanName = field.getType().getName();
                }
                Object writeValue = null;
                field.setAccessible(true);
                if (field.getType().toString().contains("interface ")) {
                    List<Class<?>> allClassByInterface = ClassUtil.getAllClassByInterface(field.getType());
                    for (Class<?> clazz : allClassByInterface) {
                        if (BeanContainer.BEAN_MAP.containsKey(clazz.getName())) {
                            writeValue = BeanContainer.BEAN_MAP.get(clazz.getName());
                            break;
                        }
                    }
                } else if (BeanContainer.BEAN_MAP.containsKey(beanName)) {
                    writeValue = BeanContainer.BEAN_MAP.get(beanName);
                } else {
                    writeValue = field.getClass().newInstance();
                    BeanContainer.BEAN_MAP.put(beanName, writeValue);
                }
                field.set(obj, writeValue);
            }
        }
    }
}
