package com.gotoubun.weddingvendor.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthRequest {
    @NotEmpty(message = "Username cannot be empty")
    private String email;
    @NotEmpty(message = "Fullname cannot be empty")
    private String name;
}