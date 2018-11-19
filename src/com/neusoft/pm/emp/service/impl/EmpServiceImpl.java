package com.neusoft.pm.emp.service.impl;

import com.neusoft.pm.emp.dao.EmpDao;
import com.neusoft.pm.emp.dao.impl.EmpDaoImpl;
import com.neusoft.pm.emp.service.EmpService;

/**
 * @Description: 员工业务层 自行添加业务规则
 * @Author Created by junwei.liang on 2018/11/19 13:25
 */
public class EmpServiceImpl implements EmpService {
    private EmpDao empDao = new EmpDaoImpl();

}
