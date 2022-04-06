package com.gotoubun.weddingvendor.data.vendorprovider;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Vendor provider request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendorProviderRequest {

    @NotBlank(message = "companyName is not blank")
    String companyName;

    @NotBlank(message = "representative is not blank")
    String representative;

    @NotBlank(message = "username is not blank")
    String username;

    String phone;

    @NotNull(message = "category is necessary to register")
    Long categoryId;

    String address;
}
