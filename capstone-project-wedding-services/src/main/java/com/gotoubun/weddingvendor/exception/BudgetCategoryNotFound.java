package com.gotoubun.weddingvendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BudgetCategoryNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BudgetCategoryNotFound(String message) {
        super(message);
    }

    {
    }
}
