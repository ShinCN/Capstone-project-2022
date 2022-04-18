package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GuestIdAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GuestIdAlreadyExistException(String message) {
        super(message);
    }
}
