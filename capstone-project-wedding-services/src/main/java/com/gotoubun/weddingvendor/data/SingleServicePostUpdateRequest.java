package com.gotoubun.weddingvendor.data;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.Value;

import java.util.Collection;
@Value
public class SingleServicePostUpdateRequest {
    Long id;
    String serviceName;
    float price;
    String description;
    Collection<Photo> imgUrl;
}
