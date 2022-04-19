package com.gotoubun.weddingvendor.data.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentResponse {
    Long id;
    String content;
    String createdBy;
}
