package com.neusoft.pm.emp.dao;

import com.neusoft.pm.emp.pojo.Emp;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface EmpDao {
    int insert(Emp emp);

    int update(Emp emp);

    int delete(Long id);

    /**
     * 根据条件查询员工列表
     *
     * @param emp
     * @return
     */
    List<Emp> queryEmpByCondition(Emp emp);
}
