package com.gotoubun.weddingvendor.data.account;

import lombok.Value;

@Value
public class AccountPasswordRequest {
    String oldPassword;
    String newPassword;
}
