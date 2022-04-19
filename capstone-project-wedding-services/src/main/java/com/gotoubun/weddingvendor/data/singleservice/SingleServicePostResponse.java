package com.gotoubun.weddingvendor.data.singleservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleServicePostResponse {
    Long id;
    String serviceName;
    Float price;
    String description;
    Collection<PhotoResponse> photos;
    String vendorAddress;
}
