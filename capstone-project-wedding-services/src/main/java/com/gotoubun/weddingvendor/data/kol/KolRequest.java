package com.gotoubun.weddingvendor.data.kol;

import lombok.Value;

@Value
public class KolRequest {
    String stageName;
    String email;
    String username;
    String phone;
    String address;
    String password;
}
