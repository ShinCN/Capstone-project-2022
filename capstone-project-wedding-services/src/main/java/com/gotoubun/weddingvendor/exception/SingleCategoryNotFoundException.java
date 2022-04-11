package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SingleCategoryNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SingleCategoryNotFoundException(String message) {
        super(message);
    }
}

