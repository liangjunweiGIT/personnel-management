package com.ljw.test;

import com.ljw.base.ioc.init.BeanContainer;
import com.ljw.base.ioc.init.InitRun;
import com.ljw.test.dao.LjwDvdDao;
import com.ljw.test.dao.impl.LjwDvdDaoImpl;
import com.ljw.test.mgr.DvdMgr;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InitRun.start();

        /*DvdMgr d = (DvdMgr) BeanContainer.getBean(DvdMgr.class);
        d.Menu();*/

        LjwDvdDao dvdDao = BeanContainer.getBean(LjwDvdDaoImpl.class);
        List<Long> longs = dvdDao.queryDVDidList();
        for (Long id : longs) {
            System.out.println(id);
        }
    }
}
