package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmptyPaymentHistoryException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmptyPaymentHistoryException(String message) {
        super(message);
    }
}