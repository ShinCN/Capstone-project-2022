package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneAlreadyExistException  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PhoneAlreadyExistException(String message) {
        super(message);
    }
}
