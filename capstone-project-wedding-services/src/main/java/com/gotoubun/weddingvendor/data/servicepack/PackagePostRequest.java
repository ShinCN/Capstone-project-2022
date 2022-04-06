package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Value
public class PackagePostRequest {
    String packTitle;
    Float price;
    String description;
    Collection<SinglePost> singlePosts;
}
