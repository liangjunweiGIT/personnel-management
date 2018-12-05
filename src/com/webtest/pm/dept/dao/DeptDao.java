package com.webtest.pm.dept.dao;

import com.webtest.pm.dept.pojo.Dept;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface DeptDao {
    List<Dept> queryList(Dept dept);

    Long insert(Dept dept);

    int update(Dept dept);

    int delete(Long id);
}
