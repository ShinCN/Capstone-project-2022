package com.gotoubun.weddingvendor.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SinglePostNameAlreadyExistsResponse {
    private String singlePostName;
}
