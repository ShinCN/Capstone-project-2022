package com.gotoubun.weddingvendor.service.comment;

import com.gotoubun.weddingvendor.data.comment.CommentRequest;
import com.gotoubun.weddingvendor.data.comment.CommentResponse;

import java.util.Collection;

public interface CommentService {

    void save(Long id, CommentRequest commentRequest, String username);
    void update(Long blogId, Long id, CommentRequest commentRequest, String username);
    void delete(Long blogId, Long id, String username);
    Collection<CommentResponse> getAllCommentByBlogId(Long id);
}
