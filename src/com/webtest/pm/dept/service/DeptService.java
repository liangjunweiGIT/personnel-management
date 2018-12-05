package com.webtest.pm.dept.service;

import com.webtest.pm.dept.pojo.Dept;
import com.webtest.pm.emp.vo.EmpVo;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:22
 */
public interface DeptService {
    /**
     * 查询岗位列表：可以按照岗位编号，名称，类型进行查询
     * @param dept
     * @return
     */
    List<Dept> queryDeptList(Dept dept);

    Long insertDept(Dept dept);

    int updateDept(Dept dept);

    int deleteDept(Long id);

    List<EmpVo> queryEmpByDeptId(Long deptId);
}
