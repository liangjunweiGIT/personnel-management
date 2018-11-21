package com.ljw.base.web.listener;

import com.ljw.base.ioc.init.InitRun;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/21 16:58
 */
public class ContextInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("IOC容器启动...");
        InitRun initRun = new InitRun();
        try {
            System.out.println("初始化Bean...");
            initRun.initClass();
            System.out.println("初始化Bean结束");
            System.out.println("依赖注入...");
            initRun.initField();
            System.out.println("依赖注入结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
