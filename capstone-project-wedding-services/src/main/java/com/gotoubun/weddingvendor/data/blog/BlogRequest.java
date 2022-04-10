package com.gotoubun.weddingvendor.data.blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogRequest {
    @NotBlank(message = "title must not be blank")
    String blogTitle;
    @NotBlank(message = "content must not be blank")
    String blogContent;
}
