package com.neusoft.pm.post.service.impl;

import com.neusoft.pm.emp.dao.EmpDao;
import com.neusoft.pm.emp.dao.impl.EmpDaoImpl;
import com.neusoft.pm.post.dao.PostDao;
import com.neusoft.pm.post.dao.impl.PostDaoImpl;
import com.neusoft.pm.post.service.PostService;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 14:01
 */
public class PostServiceImpl implements PostService {
    private PostDao postDao = new PostDaoImpl();

    private EmpDao empDao = new EmpDaoImpl();

}
