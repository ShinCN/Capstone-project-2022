package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProviderResponse {

    String username;
    String password;
    int status;
    Date createdDate;
    Date modifiedDate;
    String fullName;
    String phone;
    String address;
    String company;
    String nanoPassword;
}
