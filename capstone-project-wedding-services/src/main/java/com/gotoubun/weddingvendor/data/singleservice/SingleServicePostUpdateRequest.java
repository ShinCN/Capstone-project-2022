package com.gotoubun.weddingvendor.data.singleservice;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.Value;

import java.util.Collection;
@Value
public class SingleServicePostUpdateRequest {
    Long id;
    String serviceName;
    Float price;
    String description;
    Collection<Photo> imgUrl;
}