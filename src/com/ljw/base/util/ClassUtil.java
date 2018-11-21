package com.ljw.base.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/31 19:05
 */
public class ClassUtil {
    /**
     * 根据类对象获取实体类所有属性名和属性类型
     */
    public static Map<String, Class<?>> getFiledMapByClass(Class clazz) {
        Map<String, Class<?>> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            map.put(field.getName(), field.getType());
        }
        while (true) {
            clazz = clazz.getSuperclass();
            if (clazz.equals(Object.class)) {
                return map;
            }
            fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                map.put(field.getName(), field.getType());
            }
        }
    }

    /**
     * 根据实例对象获取实体类所有属性名和属性类型
     */
    public static Map<String, Class<?>> getFiledMapByObj(Object obj) {
        return getFiledMapByClass(obj.getClass());
    }

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object obj) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter);
            return method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据属性名设置属性值
     */
    public static void setFieldValueByName(Object obj, Class clazz, String fieldName, Object value) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(setter, clazz);
            if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
                method.invoke(obj, Integer.valueOf(value.toString()));
            } else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
                method.invoke(obj, Long.valueOf(value.toString()));
            } else if (float.class.equals(clazz) || Float.class.equals(clazz)) {
                method.invoke(obj, Float.valueOf(value.toString()));
            } else if (double.class.equals(clazz) || Double.class.equals(clazz)) {
                method.invoke(obj, Double.valueOf(value.toString()));
            } else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
                method.invoke(obj, Boolean.valueOf(value.toString()));
            } else {
                method.invoke(obj, value);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型
     * 或者String||StringBuffer||StringBuilder
     */
    public static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return String.class.equals(obj.getClass()) || StringBuffer.class.equals(obj.getClass()) || StringBuilder.class.equals(obj.getClass());
        }
    }

    /**
     * @param c 接口
     * @return List<Class<?>>    实现接口的所有类
     * @Description: 根据一个接口返回该接口的所有类
     */
    @SuppressWarnings("unchecked")
    public static List<Class<?>> getAllClassByInterface(Class c) {
        List returnClassList = new ArrayList<Class<?>>();
        //判断是不是接口,不是接口不作处理
        if (c.isInterface()) {
            String packageName = c.getPackage().getName();    //获得当前包名
            try {
                List<Class<?>> allClass = getClasses(packageName);//获得当前包以及子包下的所有类

                //判断是否是一个接口
                for (Class allClas : allClass) {
                    if (c.isAssignableFrom(allClas)) {
                        if (!c.equals(allClas)) {
                            returnClassList.add(allClas);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return returnClassList;
    }

    /**
     * @param pageName 包名
     * @return List<Class<?>>    包下所有类
     * @Description: 根据包名获得该包以及子包下的所有类不查找jar包中的
     */
    private static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String newPath = resource.getFile().replace("%20", " ");
            dirs.add(new File(newPath));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClass(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClass(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClass(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    public static List<Class<?>> getAllClassByAnnotation(Class annotationClass) {
        List returnClassList = new ArrayList<Class<?>>();
        //判断是不是注解
        if (annotationClass.isAnnotation()) {
            String packageName = annotationClass.getPackage().getName();    //获得当前包名
            try {
                List<Class<?>> allClass = getClasses(packageName);//获得当前包以及子包下的所有类

                for (Class allClas : allClass) {
                    if (allClas.isAnnotationPresent(annotationClass)) {
                        returnClassList.add(allClas);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return returnClassList;
    }

}
