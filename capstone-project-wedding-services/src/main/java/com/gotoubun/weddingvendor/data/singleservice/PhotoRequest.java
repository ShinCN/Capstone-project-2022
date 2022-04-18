package com.gotoubun.weddingvendor.data.singleservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhotoRequest {
    String caption;
    String url;
}
