package com.gotoubun.weddingvendor.data.singleservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponse {
    Long id;
    String caption;
    String url;
}
