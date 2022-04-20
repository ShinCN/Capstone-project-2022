package com.gotoubun.weddingvendor.data.guest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestResponse {
    String id;
    String fullName;
    String phone;
    String mail;
    String address;
}
