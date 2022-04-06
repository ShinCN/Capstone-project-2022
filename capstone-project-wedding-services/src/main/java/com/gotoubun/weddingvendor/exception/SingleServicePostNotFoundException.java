package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SingleServicePostNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SingleServicePostNotFoundException(String message) {
        super(message);
    }

}

