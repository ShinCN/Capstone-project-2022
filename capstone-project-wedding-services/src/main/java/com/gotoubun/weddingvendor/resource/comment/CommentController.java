package com.gotoubun.weddingvendor.resource.comment;

import com.gotoubun.weddingvendor.data.comment.CommentRequest;
import com.gotoubun.weddingvendor.data.comment.CommentResponse;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.comment.CommentService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/blog/detail/{blogId}/comment")
public class CommentController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> postComment(@Valid @PathVariable(name = "blogId") Long postId, @RequestBody CommentRequest request,
                                         BindingResult bindingResult,
                                         Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        commentService.save(postId, request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putComment(@Valid @PathVariable(name = "blogId") Long blogId,
                                        @PathVariable(name = "id") Long id,
                                        @RequestBody CommentRequest request,
                                        BindingResult bindingResult,
                                        Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        commentService.update(blogId, id, request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Collection<CommentResponse> getAllCommentInBlog(@PathVariable(name = "blogId") Long blogId) {
        return commentService.getAllCommentByBlogId(blogId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "blogId") Long blogId,
                                           @PathVariable(name = "id") Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);

        commentService.delete(blogId, id, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);

    }
}
