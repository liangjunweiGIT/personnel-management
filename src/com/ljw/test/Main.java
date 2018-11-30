package com.ljw.test;

import com.ljw.base.ioc.init.BeanContainer;
import com.ljw.base.ioc.init.InitRun;
import com.ljw.test.mgr.DvdMgr;

public class Main {
    public static void main(String[] args) {
        InitRun.start();

        DvdMgr d = (DvdMgr) BeanContainer.getBean(DvdMgr.class);
        d.Menu();
    }

}
