package com.gotoubun.weddingvendor.service;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostNewRequest;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.Collection;
import java.util.Date;

public interface PackagePostService {
    PackagePost save(PackagePostNewRequest packagePostNewRequest, String username);
    PackagePost updateTitle(Long id, String serviceName, String username);
    PackagePost updateAbout(Long id, String about, String username);
    PackagePost updateServiceList(Long id, Collection<SinglePost> singlePosts, String username);
    void delete(Long id);
}
