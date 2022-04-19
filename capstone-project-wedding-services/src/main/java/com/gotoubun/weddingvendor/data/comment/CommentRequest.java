package com.gotoubun.weddingvendor.data.comment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {
    @NotBlank(message = "content should not be blank")
    String content;
}
