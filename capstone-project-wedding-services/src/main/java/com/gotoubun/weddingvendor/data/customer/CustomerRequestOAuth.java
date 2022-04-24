package com.gotoubun.weddingvendor.data.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRequestOAuth {
    String fullName;
    String email;
    String password;
}