package com.neusoft.pm.emp.dao;

import com.neusoft.pm.emp.pojo.Emp;
import com.neusoft.pm.emp.vo.EmpVo;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface EmpDao {
    Long insert(Emp emp);

    int update(Emp emp);

    int delete(Long id);

    /**
     * 根据条件查询员工列表
     *
     * @param emp
     * @return
     */
    List<EmpVo> queryEmpByCondition(EmpVo emp);
}
