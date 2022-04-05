package com.gotoubun.weddingvendor.service;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

public interface PackagePostService {
    PackagePost save(PackagePostRequest packagePostNewRequest, String username);
    PackagePost update(Long id, PackagePostRequest packagePostNewRequest, String username);
    void delete(Long id);
}
