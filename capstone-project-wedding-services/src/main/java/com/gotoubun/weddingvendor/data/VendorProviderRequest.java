package com.gotoubun.weddingvendor.data;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Vendor provider request.
 */
@Value
public class VendorProviderRequest {

     @NotBlank(message = "companyName is not blank")
     String companyName;

     @NotBlank(message = "username is not blank")
     String username;

     String phone;

     @NotNull(message = "category is necessary to register")
     Long categoryId;

     String address;
}
