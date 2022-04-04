package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

@NoArgsConstructor
@Data
public class PackageUpdateServiceList {
    Collection<SinglePost> singlePosts;
    Float price;
}
