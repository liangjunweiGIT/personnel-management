package com.ljw.test.service.impl;

import com.ljw.base.ioc.annotation.Service;
import com.ljw.test.service.LjwDvdService;
import com.ljw.test.service.TestService;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/12/10 11:11
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private LjwDvdService ljwDvdService;

    @Override
    public void sy() {
        System.out.println("say hello");
    }
}
