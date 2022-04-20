package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProviderResponse {

    Long id;
    String username;
    boolean status;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    String fullName;
    String phone;
    String address;
    String company;
    String nanoPassword;
}
