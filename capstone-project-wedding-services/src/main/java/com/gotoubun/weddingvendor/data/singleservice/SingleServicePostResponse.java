package com.gotoubun.weddingvendor.data.singleservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleServicePostResponse {
    Long id;
    String serviceName;
    Float price;
    String description;
    float rate;
    Collection<PhotoResponse> photos;
    String vendorAddress;
    String singleCategoryName;
}
