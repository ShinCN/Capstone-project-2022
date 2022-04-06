package com.gotoubun.weddingvendor.data.blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogRequest {
    String title;
    String content;
    String createdBy;
}
