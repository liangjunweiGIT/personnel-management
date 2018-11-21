package com.ljw.test;

import com.ljw.base.ioc.init.BeanContainer;
import com.ljw.base.ioc.init.InitRun;
import com.ljw.base.util.PropertiesUtil;
import com.ljw.test.mgr.DvdMgr;

public class Main {
    public static void main(String[] args) {
        System.out.println("IOC容器启动...");
        InitRun initRun = new InitRun();
        try {
            System.out.println("初始化Bean...");
            initRun.initClass();
            System.out.println("依赖注入...");
            initRun.initField();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DvdMgr d = (DvdMgr) BeanContainer.getBean(DvdMgr.class);
        assert d != null;
        d.Menu();
    }

}
