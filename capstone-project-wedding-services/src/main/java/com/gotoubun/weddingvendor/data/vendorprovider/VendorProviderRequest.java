package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
}