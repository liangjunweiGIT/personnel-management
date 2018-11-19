package com.neusoft.pm.dept.dao;

import com.neusoft.pm.dept.pojo.Dept;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface DeptDao {
    List<Dept> queryList(Dept dept);

    int insert(Dept dept);

    int update(Dept dept);

    int delete(Long id);
}
