package com.gotoubun.weddingvendor.data;

import lombok.Value;

@Value
public class VendorProviderRequest {
     String companyName;
     String username;
     String phone;
     long categoryId;
     String address;
}
