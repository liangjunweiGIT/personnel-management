package com.neusoft.pm.emp.pojo;

/**
 * @Description: 员工信息 自行添加其他属性 和主表字段一一对应
 * @Author Created by junwei.liang on 2018/11/19 13:23
 */
public class Emp {
    /**
     * 主键
     */
    private Long id;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 岗位id
     */
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
