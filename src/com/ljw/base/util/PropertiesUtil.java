package com.ljw.base.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/30 17:12
 */
public class PropertiesUtil {
    private static Properties PROP;

    static {
        PROP = new Properties();
        try {
            PROP.load(DbHelper.class.getClassLoader().getResourceAsStream("DVD.ut"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return PROP.getProperty(key);
    }

    public static Object getObject(String key) {
        try {
            return Class.forName(PROP.getProperty(key)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
