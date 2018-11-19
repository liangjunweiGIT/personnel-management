package com.neusoft.pm.emp.dao.impl;

import com.ljw.base.db.BaseDao;
import com.neusoft.pm.emp.dao.EmpDao;
import com.neusoft.pm.emp.pojo.Emp;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:31
 */
public class EmpDaoImpl extends BaseDao implements EmpDao {
    @Override
    public int insert(Emp emp) {
        return 0;
    }

    @Override
    public int update(Emp emp) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public List<Emp> queryEmpByCondition(Emp emp) {
        String sql = "select * from t_emp where 1=1 ";
        if (emp.getDeptId() != null) {
            sql += "and dept_id = #deptId#";
        }
        if (emp.getPostId() != null) {
            sql += "and post_id = #postId#";
        }
        return super.queryForList(sql, Emp.class, emp);
    }
}
