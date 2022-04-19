package com.gotoubun.weddingvendor.service.comment.impl;

import com.gotoubun.weddingvendor.data.comment.CommentRequest;
import com.gotoubun.weddingvendor.data.comment.CommentResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.Blog;
import com.gotoubun.weddingvendor.domain.vendor.Comment;
import com.gotoubun.weddingvendor.exception.CommentNotBelongBlogException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CommentRepository;
import com.gotoubun.weddingvendor.service.blog.BlogService;
import com.gotoubun.weddingvendor.service.comment.CommentService;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Override
    public void save(Long id, CommentRequest commentRequest, String username) {
        Account account = accountRepository.findByUsername(username);
        Optional<Blog> blog = blogService.findBlogById(id);

        Comment newComment = new Comment();
        newComment.setAccount(account);
        newComment.setBlog(blog.get());
        newComment.setContent(commentRequest.getContent());
        newComment.setCreatedDate(getCurrentDate.now());
        newComment.setCreatedBy(username);

        commentRepository.save(newComment);
    }

    @Override
    public void update(Long blogId, Long id, CommentRequest commentRequest, String username) {

        Optional<Blog> blog = Optional.of(blogService.findBlogById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("This blog does not exist.")));
        Optional<Comment> comment = Optional.of(commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This content does not exist.")));

        if (!comment.get().getBlog().getId().equals(blog.get().getId())) {
            throw new CommentNotBelongBlogException("This comment does not belong to this blog.");
        }

        if (comment.get().getAccount().getUsername().equalsIgnoreCase(username)){
            comment.get().setContent(commentRequest.getContent());
            commentRepository.save(comment.get());
        }
    }

    @Override
    public void delete(Long blogId, Long id, String username) {
        Optional<Blog> blog = Optional.of(blogService.findBlogById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("This blog does not exist.")));
        Optional<Comment> comment = Optional.of(commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This content does not exist.")));

        if (!comment.get().getBlog().getId().equals(blog.get().getId())) {
            throw new CommentNotBelongBlogException("This comment does not belong to this blog.");
        }

        if (comment.get().getAccount().getUsername().equalsIgnoreCase(username)){
            commentRepository.delete(comment.get());
        }
    }


    @Override
    public Collection<CommentResponse> getAllCommentByBlogId(Long id) {
        Collection<Comment> comments = commentRepository.findAllByBlog_Id(id);
        List<CommentResponse> commentResponses = new ArrayList<>();
        comments.forEach(c -> {
                CommentResponse commentResponse = CommentResponse.builder()
                            .id(c.getId())
                            .content(c.getContent())
                            .createdBy(c.getCreatedBy())
                            .build();
            commentResponses.add(commentResponse);
                }
        );
        return commentResponses;
    }

}
