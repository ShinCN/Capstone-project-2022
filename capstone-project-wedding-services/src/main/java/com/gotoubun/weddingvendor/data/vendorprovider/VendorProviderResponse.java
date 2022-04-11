package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProviderResponse {

    Long id;
    String username;
    int status;
    Date createdDate;
    Date modifiedDate;
    String fullName;
    String phone;
    String address;
    String company;
    String nanoPassword;
}
