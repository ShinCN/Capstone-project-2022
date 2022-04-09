package com.gotoubun.weddingvendor.service.blog;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.domain.vendor.Blog;

import java.util.Collection;

public interface BlogService {
    Collection<Blog> findAll();
    Blog save(BlogRequest blogRequest, String username);
    Collection<Blog> findAllByUserName(String username);
    Blog getBlogDetailById(Long id);
    Blog update(Long id, BlogRequest blogRequest);
    void delete(Long id);
}
