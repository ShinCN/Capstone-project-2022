package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class VendorProviderRequest {
    @NotBlank(message = "companyName field must not be blank")
    String companyName;

    @NotBlank(message = "email field must not be blank")
    String email;

    @NotBlank(message = "phone field must not be blank")
    String phone;

    Long categoryId;
    String anotherService;

    @NotBlank(message = "address field must not be blank")
    String address;

    String password;
}