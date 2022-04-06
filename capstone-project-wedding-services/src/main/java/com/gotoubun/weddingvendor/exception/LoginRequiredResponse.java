package com.gotoubun.weddingvendor.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequiredResponse {
    private String message;

    public LoginRequiredResponse(String message) {
        this.message = "you have to login to get access";
    }

}

