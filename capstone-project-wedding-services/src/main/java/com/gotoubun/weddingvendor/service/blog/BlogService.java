package com.gotoubun.weddingvendor.service.blog;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Blog;

import java.util.Collection;
import java.util.Optional;

public interface BlogService {
    Collection<Blog> findAll();
    Blog save(BlogRequest blogRequest, String username);
    Collection<Blog> findAllByAccount(Account account);
    Optional<Blog> findBlogById(Long id);
    Blog update(Long id, BlogRequest blogRequest, String username);
    void delete(Long id);
}
