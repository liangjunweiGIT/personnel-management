package com.neusoft.pm.post.dao;

import com.neusoft.pm.dept.pojo.Dept;
import com.neusoft.pm.post.pojo.Post;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface PostDao {
    List<Post> queryList(Post post);

    int insert(Post post);

    int update(Post post);

    int delete(Long id);
}
