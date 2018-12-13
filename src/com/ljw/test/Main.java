package com.ljw.test;

import com.ljw.base.ioc.annotation.Bean;
import com.ljw.base.ioc.init.BeanContainer;
import com.ljw.base.ioc.init.InitRun;
import com.ljw.test.dao.LjwDvdDao;
import com.ljw.test.dao.impl.LjwDvdDaoImpl;
import com.ljw.test.mgr.DvdMgr;
import com.ljw.test.pojo.LjwDvd;
import com.ljw.test.service.LjwDvdService;
import com.ljw.test.service.impl.LjwDvdServiceImpl;
import com.ljw.test.vo.DvdVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Bean
public class Main {

    @Resource
    private static LjwDvdService ljwDvdService;

    public static void main(String[] args) {
        InitRun.start();
        LjwDvdDao dvdDao = BeanContainer.getBean(LjwDvdDaoImpl.class);
        DvdVO dvdVO = dvdDao.queryDVDById(1L);
        System.out.println(dvdVO.getTypeName());
       /* LjwDvdService bean = BeanContainer.getBean(LjwDvdServiceImpl.class);
        bean.test();
        System.out.println(Integer.MAX_VALUE);*/
       // ((LjwDvdServiceImpl)bean).test();
        //bean.test();
        /*DvdMgr d = (DvdMgr) BeanContainer.getBean(DvdMgr.class);
        d.Menu();*/

        /*LjwDvdDao dvdDao = BeanContainer.getBean(LjwDvdDaoImpl.class);
        List<LjwDvd> dvds = new ArrayList<>();
        LjwDvd dvd1 = new LjwDvd();
        dvd1.setId(100L);
        LjwDvd dvd2 = new LjwDvd();
        dvd2.setId(101L);
        LjwDvd dvd3 = new LjwDvd();
        dvd3.setId(102L);
        LjwDvd dvd4 = new LjwDvd();
        dvd4.setId(103L);
        dvds.add(dvd1);
        dvds.add(dvd2);
        dvds.add(dvd3);
        dvds.add(dvd4);
        dvdDao.addDVDList(dvds);
        List<Long> longs = dvdDao.queryDVDidList();
        for (Long id : longs) {
            System.out.println(id);
        }*/
    }
}
