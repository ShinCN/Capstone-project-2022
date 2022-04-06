package com.gotoubun.weddingvendor.service.service_pack;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

public interface PackagePostService {
    PackagePost save(PackagePostRequest packagePostNewRequest, String username);
    PackagePost updateDescription(Long id, PackagePostRequest packagePostNewRequest, String username);
    void delete(Long id);
}
