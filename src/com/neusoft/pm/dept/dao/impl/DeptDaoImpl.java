package com.neusoft.pm.dept.dao.impl;

import com.ljw.base.db.BaseDao;
import com.ljw.base.ioc.annotation.Bean;
import com.neusoft.pm.dept.dao.DeptDao;
import com.neusoft.pm.dept.pojo.Dept;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:20
 */
@Bean
public class DeptDaoImpl extends BaseDao implements DeptDao {

    @Override
    public List<Dept> queryList(Dept dept) {
        return queryForList("select * from t_dept", Dept.class, dept);
    }

    @Override
    public Long insert(Dept dept) {
        return insert("insert into dept() values()",dept,"select dept_tb_seq.currval as id from dual");
    }

    @Override
    public int update(Dept dept) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
