package com.gotoubun.weddingvendor.data;

import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * The type Kol request.
 */
@Value
public class KOLRequest {
    @NotBlank(message = "name is not blank")
    String fullName;

    @NotBlank(message = "email is not blank")
    String email;

    @NotBlank(message = "password is not blank")
    String password;

    @NotBlank(message = "phone is not blank")
    String phone;

    String address;

    String description;
}
