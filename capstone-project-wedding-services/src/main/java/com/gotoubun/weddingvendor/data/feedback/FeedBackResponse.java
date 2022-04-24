package com.gotoubun.weddingvendor.data.feedback;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FeedBackResponse {
    Long id;
    String content;
    String createdBy;
}