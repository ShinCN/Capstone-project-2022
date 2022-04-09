package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccountNotHaveAccessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AccountNotHaveAccessException(String message) {
        super(message);
    }

}

