package com.gotoubun.weddingvendor.data.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    Long id;
    String username;
    int status;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    String fullName;
    String phone;
    String address;
    String password;
    LocalDate weddingDate;
}
