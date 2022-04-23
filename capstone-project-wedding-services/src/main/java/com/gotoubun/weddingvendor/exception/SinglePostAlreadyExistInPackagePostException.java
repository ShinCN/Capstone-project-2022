package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SinglePostAlreadyExistInPackagePostException extends RuntimeException {
    public SinglePostAlreadyExistInPackagePostException(String message) {
        super(message);
    }
}
