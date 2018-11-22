package com.neusoft.pm.dept.service.impl;

import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;
import com.neusoft.pm.dept.dao.DeptDao;
import com.neusoft.pm.dept.pojo.Dept;
import com.neusoft.pm.dept.service.DeptService;
import com.neusoft.pm.emp.dao.EmpDao;
import com.neusoft.pm.emp.vo.EmpVo;

import java.util.List;

/**
 * @Description: 部门业务层 自行添加业务规则
 * @Author Created by junwei.liang on 2018/11/19 13:25
 */
@Bean
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private EmpDao empDao;

    @Override
    public List<Dept> queryDeptList(Dept dept) {
        return deptDao.queryList(dept);
    }

    @Override
    public Long insertDept(Dept dept) {
        return deptDao.insert(dept);
    }

    @Override
    public int updateDept(Dept dept) {
        return deptDao.update(dept);
    }

    @Override
    public int deleteDept(Long id) {
        //TODO 删除岗位必须保证岗位上没有配置员工
        return deptDao.delete(id);
    }

    @Override
    public List<EmpVo> queryEmpByDeptId(Long deptId) {
        EmpVo emp = new EmpVo();
        emp.setDeptId(deptId);
        return empDao.queryEmpByCondition(emp);
    }
}
