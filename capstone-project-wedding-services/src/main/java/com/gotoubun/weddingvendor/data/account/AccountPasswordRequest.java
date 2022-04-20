package com.gotoubun.weddingvendor.data.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountPasswordRequest {
    @NotBlank(message = "Please enter your current password")
    String oldPassword;
    @NotBlank(message = "Please enter your new password")
    String newPassword;
}
