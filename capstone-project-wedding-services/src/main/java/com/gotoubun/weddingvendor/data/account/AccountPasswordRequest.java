package com.gotoubun.weddingvendor.data.account;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class AccountPasswordRequest {
    @NotBlank(message = "Please enter your current password")
    String oldPassword;
    @NotBlank(message = "Please enter your new password")
    String newPassword;
}
