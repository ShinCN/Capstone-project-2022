package com.gotoubun.weddingvendor.service.blog.impl;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.Blog;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.BlogRepository;
import com.gotoubun.weddingvendor.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BlogRepository blogRepository;

    //Find all blogs
    @Override
    public Collection<Blog> findAll() {
        Collection<Blog> blogs = blogRepository.findAll();
        return  blogs;
    }

    //Add 1 new blog
    @Override
    public Blog save(BlogRequest request, String username) {
        Account account = accountRepository.findByUsername(username);

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCreatedBy(account.getUsername());
        return blogRepository.save(blog);

    }

    @Override
    public Collection<Blog> findAllByUserName(String username) {
        return null;
    }

    @Override
    public Blog getBlogDetailById(Long id) {
        return null;
    }

    @Override
    public Blog update(Long id, BlogRequest blogRequest) {
        return null;
    }


    //delete 1 blog
    @Override
    public void delete(Long id) {
        blogRepository.delete(getServicePostById(id).get());
    }
    public Optional<Blog> getServicePostById(Long id) {
        return Optional.ofNullable(blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }
}
