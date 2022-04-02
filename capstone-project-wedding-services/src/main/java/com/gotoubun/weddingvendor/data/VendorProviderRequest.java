package com.gotoubun.weddingvendor.data;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import lombok.Value;

@Value
public class VendorProviderRequest {
    String companyName;
    String representative;
    String email;
    String username;
    String phone;
    Long categoryId;
    String anotherService;
    String address;
    String password;
}