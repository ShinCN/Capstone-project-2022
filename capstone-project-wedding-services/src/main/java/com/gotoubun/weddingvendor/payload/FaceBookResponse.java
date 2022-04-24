package com.gotoubun.weddingvendor.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaceBookResponse {
    private String token;
    private String username;
}