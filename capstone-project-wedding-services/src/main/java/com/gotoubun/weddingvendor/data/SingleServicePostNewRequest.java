package com.gotoubun.weddingvendor.data;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.Value;

import java.util.Collection;

@Value
public class SingleServicePostNewRequest {
    String serviceName;
    float price;
    String description;
    Collection<Photo> photos;
}
