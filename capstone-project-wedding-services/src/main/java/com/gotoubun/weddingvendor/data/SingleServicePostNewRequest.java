package com.gotoubun.weddingvendor.data;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.Value;

import java.util.Collection;

@Value
public class SingleServicePostNewRequest {
    String serviceName;
    Float price;
    String description;
    Collection<Photo> photos;
}
