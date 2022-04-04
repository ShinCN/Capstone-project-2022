package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.Value;
import java.util.Collection;

@Value
public class PackagePostNewRequest {

    String packTitle;
    Float price;
    String description;
    Collection<SinglePost> singlePosts;

}
