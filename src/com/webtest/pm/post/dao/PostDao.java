package com.webtest.pm.post.dao;

import com.webtest.pm.post.pojo.Post;

import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/19 13:17
 */
public interface PostDao {
    List<Post> queryList(Post post);

    Long insert(Post post);

    int update(Post post);

    int delete(Long id);
}
