package com.webtest.pm.emp.dao;

import com.webtest.pm.emp.pojo.Emp;
import com.webtest.pm.emp.vo.EmpVo;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/21 10:35
 */
public interface EntryHistoryDao {
    /**
     * 插入员工入职历史记录
     *
     * @return
     */
    int insertHistory(Emp emp);

    /**
     * 查询员工入职历史
     *
     * @return
     */
    List<EmpVo> queryHistoryList();
}
