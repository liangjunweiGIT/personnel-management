package com.webtest.pm.post.service.impl;

import com.webtest.pm.emp.dao.EmpDao;
import com.webtest.pm.emp.dao.impl.EmpDaoImpl;
import com.webtest.pm.post.dao.PostDao;
import com.webtest.pm.post.dao.impl.PostDaoImpl;
import com.webtest.pm.post.service.PostService;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 14:01
 */
public class PostServiceImpl implements PostService {
    private PostDao postDao = new PostDaoImpl();

    private EmpDao empDao = new EmpDaoImpl();

}
