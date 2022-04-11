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

/**
 * The type Blog controller.
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    /**
     * The Account repository.
     */
    @Autowired
    AccountRepository accountRepository;

    /**
     * The Blog service.
     */
    @Autowired
    BlogService blogService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Get all blogs collection.
     *
     * @return the collection
     */
//get all blogs to show on ideas of all actors
    @GetMapping
    public Collection<Blog> getAllBlogs() {
        return blogService.findAll();
    }

    /**
     * Get blog detail optional.
     *
     * @param id the id
     * @return the optional
     */

    @GetMapping("detail/{id}")
    public Optional<Blog> getBlogDetail(@PathVariable Long id) {
        return blogService.findBlogById(id);
    }

    /**
     * Post blog response entity.
     *
     * @param request       the request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */

    @PostMapping
    public ResponseEntity<?> postBlog(@Valid @RequestBody BlogRequest request,
                                      BindingResult bindingResult,
                                      Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        blogService.save(request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put blog response entity.
     *
     * @param id            the id
     * @param request       the request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */

    @PutMapping("{id}")
    public ResponseEntity<?> putBlog(@Valid @PathVariable Long id,
                                     @RequestBody BlogRequest request,
                                     BindingResult bindingResult,
                                     Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        blogService.update(id, request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Get all blogs by user collection.
     *
     * @param principal the principal
     * @return the collection
     */

    @GetMapping("my-blogs")
    public Collection<Blog> getAllBlogsByUser(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        Account account = accountRepository.findByUsername(principal.getName());
        return blogService.findAllByAccount(account);
    }

    /**
     * Delete blog response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping
    public ResponseEntity<?> deleteBlog(@PathVariable Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);

        blogService.delete(id);
        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);

    }
}
