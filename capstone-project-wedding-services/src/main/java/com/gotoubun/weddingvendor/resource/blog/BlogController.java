package com.gotoubun.weddingvendor.resource.blog;

import com.gotoubun.weddingvendor.data.blog.BlogRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Blog;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.service.blog.BlogService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    //get all blogs to show on ideas of all actors
    @GetMapping
    public Collection<Blog> getAllBlogs(){
        return blogService.findAll();
    }

    //view a blog by id
    @GetMapping("detail/{id}")
    public Optional<Blog> getBlogDetail(@PathVariable Long id){
        return blogService.findBlogById(id);
    }

    //create new blog after login
    @PostMapping
    public ResponseEntity<?> postBlog(@Valid @RequestBody BlogRequest request, BindingResult bindingResult, Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        blogService.save(request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }


    //update a blog after login
    @PutMapping("{id}")
    public ResponseEntity<?> putBlog(@Valid @PathVariable Long id, @RequestBody BlogRequest request, BindingResult bindingResult, Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        blogService.update(id, request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


    //get all blogs by user
    @GetMapping("my-blogs")
    public Collection<Blog> getAllBlogsByUser(Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        Account account = accountRepository.findByUsername(principal.getName());
        return blogService.findAllByAccount(account);
    }


    //delete a blog
    @DeleteMapping
    public ResponseEntity<?> deleteBlog(@PathVariable Long id, Principal principal){
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");

        blogService.delete(id);
        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

}
