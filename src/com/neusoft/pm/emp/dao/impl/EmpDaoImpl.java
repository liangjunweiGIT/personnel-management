package com.neusoft.pm.emp.dao.impl;

import com.ljw.base.db.BaseDao;
import com.neusoft.pm.emp.dao.EmpDao;
import com.neusoft.pm.emp.pojo.Emp;
import com.neusoft.pm.emp.vo.EmpVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:31
 */
public class EmpDaoImpl extends BaseDao implements EmpDao {
    @Override
    public Long insert(Emp emp) {
        //第一种写法:
        /*//注意 已经在表中设置主键自增 所以新增时不要插入主键
        int insert = super.insert("insert into t_emp(dept_id,post_id,........) values(#deptId#,#postId#,........)", emp);
        //获取刚刚插入的主键
        Map map = super.queryForObject("select emp_tb_seq.currval from dual", Map.class);
        //此时emp对象已经包含id
        emp.setId(Long.valueOf(map.get("currval").toString()));
        return emp.getId();*/

        //第二种写法: 使用带after参数的insert方法 注意查询结果列名要设置为跟主键名一直("as id") 给emp对象id赋值的操作会在SqlExecutor内部实现
        return insert("insert into t_emp(dept_id,post_id) values(#deptId#,#postId#)", emp,
                "select emp_tb_seq.currval as id from dual");
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
    public List<EmpVo> queryEmpByCondition(EmpVo emp) {
        String sql = "select * from t_emp where 1=1 ";
        addIfNotNull(sql, emp.getId(), "and id = #id#");
        addIfNotNull(sql, emp.getDeptId(), "and dept_id = #deptId#");
        addIfNotNull(sql, emp.getPostId(), "and post_id = #postId#");
        return queryForList(sql, EmpVo.class, emp);
    }
}
