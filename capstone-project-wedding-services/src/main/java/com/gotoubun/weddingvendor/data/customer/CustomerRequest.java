package com.gotoubun.weddingvendor.data.customer;

import lombok.Value;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Value
public class CustomerRequest {
    @NotBlank(message = "name is not blank")
    String fullName;
    @NotBlank(message = "email is not blank")
    String email;
    @NotBlank(message = "password is not blank")
    String password;
    @NotBlank(message = "phone is not blank")
    String phone;

    String address;

    @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}",message = "Please enter correct date format")
    String weddingDate;
}
