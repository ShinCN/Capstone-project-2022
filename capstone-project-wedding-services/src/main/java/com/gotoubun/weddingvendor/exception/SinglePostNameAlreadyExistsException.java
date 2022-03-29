package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SinglePostNameAlreadyExistsException extends RuntimeException{
    public SinglePostNameAlreadyExistsException(String message) {
        super(message);
    }
}
