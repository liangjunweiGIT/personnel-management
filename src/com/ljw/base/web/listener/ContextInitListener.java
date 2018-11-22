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
        InitRun.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
