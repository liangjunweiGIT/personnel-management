package com.webtest.pm.emp.vo;

import com.webtest.pm.emp.pojo.Emp;

/**
 * @Description: 主表字段扩展 for关联查询
 * @Author Created by junwei.liang on 2018/11/21 10:37
 */
public class EmpVo extends Emp {
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 岗位名
     */
    private String postName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
