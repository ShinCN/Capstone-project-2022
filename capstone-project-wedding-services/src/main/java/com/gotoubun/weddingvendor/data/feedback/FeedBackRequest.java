package com.gotoubun.weddingvendor.data.feedback;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedBackRequest {
    String content;
    Integer rate;
}
