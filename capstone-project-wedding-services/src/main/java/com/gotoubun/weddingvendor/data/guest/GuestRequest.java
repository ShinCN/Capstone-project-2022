package com.gotoubun.weddingvendor.data.guest;

import lombok.Value;

import javax.validation.constraints.NotBlank;
@Value
public class GuestRequest {
    @NotBlank(message = "Name is not blank")
    String fullName;
    @NotBlank(message = "Phone is not blank")
    String phone;
    @NotBlank(message = "Email is not blank")
    String mail;
    String address;
}
