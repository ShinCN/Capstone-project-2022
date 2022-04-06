package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class LoginRequiredException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LoginRequiredException(String message) {
        super(message);
    }

}

