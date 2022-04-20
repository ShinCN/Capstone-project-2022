package com.gotoubun.weddingvendor.service.blog.impl;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Blog;
import com.gotoubun.weddingvendor.exception.BlogExistedException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.BlogRepository;
import com.gotoubun.weddingvendor.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
        return blogs;
    }

    //view 1 blog details
    @Override
    public Optional<Blog> findBlogById(Long id) {
        return Optional.ofNullable(blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }
    //Add new blog
    @Override
    public Blog save(BlogRequest request, String username) {
        Account account = accountRepository.findByUsername(username);

        if (!checkBlogNameExisted(request.getBlogTitle(), account)) {
            throw new BlogExistedException("Blog title " + request.getBlogTitle() + " has already existed");
        }
        Blog blog = new Blog();
        blog.setTitle(request.getBlogTitle());
        blog.setContent(request.getBlogContent());
        blog.setCreatedBy(account.getUsername());
        blog.setAccount(account);
        return blogRepository.save(blog);
    }

    @Override
    public Collection<Blog> findAllByAccount(Account account) {
        return blogRepository.findAllByAccount(account) ;
    }


    //update a service
    @Override
    public Blog update(Long id, BlogRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        if (!checkBlogNameExisted(request.getBlogTitle(), account)) {
            throw new BlogExistedException("Blog title " + request.getBlogTitle() + " has already existed");
        }
        Blog blog = blogRepository.getById(id);
        blog.setTitle(request.getBlogTitle());
        blog.setContent(request.getBlogContent());
        blog.setModifiedBy(account.getUsername());
        return blogRepository.save(blog);
    }

    //delete 1 blog
    @Override
    public void delete(Long id) {
        blogRepository.delete(findBlogById(id).get());
    }

    boolean checkBlogNameExisted(String blogTitle, Account account) {
        List<Blog> blogs = blogRepository.findAllByAccount(account);

        for(Blog b : blogs){
            if(b.getTitle().equalsIgnoreCase(blogTitle)){
                return false;
            }
        }
        return true;
    }
}
