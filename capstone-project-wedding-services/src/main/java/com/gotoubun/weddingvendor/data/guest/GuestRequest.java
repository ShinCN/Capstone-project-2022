package com.gotoubun.weddingvendor.data.guest;

import lombok.Value;

import javax.validation.constraints.NotBlank;
@Value
public class GuestRequest {
    @NotBlank(message = "Name is not blank")
    String fullName;
    String phone;
    String mail;
    String address;

}
