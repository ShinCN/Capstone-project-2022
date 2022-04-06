package com.gotoubun.weddingvendor.resource.blog;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Blog;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.account.impl.AccountServiceImpl;
import com.gotoubun.weddingvendor.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/ideas")
public class BlogController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BlogService blogService;

    //get all blog
    @GetMapping
    public Collection<Blog> getAllBlogs(){
        Collection<Blog> blogs = blogService.findAll();
        return blogs;
    }

    //get all blog by user
    @GetMapping("my-blogs")
    public Collection<Blog> getAllBlogsByUser(Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role == 1) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        Collection<Blog> blogs = blogService.findAllByUserName(principal.getName());
        return blogs;
    }

    //get blog detail by user id
    @GetMapping("detail/{id}")
    public Blog getBlogDetail(@PathVariable Long id, Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role == 1) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        Blog blog = blogService.getBlogDetailById(id);
        return blog;
    }


}
