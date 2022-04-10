package com.gotoubun.weddingvendor.service.service_pack;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PackagePostService {
    void save(PackagePostRequest packagePostNewRequest, String username);
    PackagePost update(Long id, PackagePostRequest packagePostNewRequest, String username);
    void delete(Long id);
    List<PackagePostResponse> findAll( String keyWord, Long packageId, Float price);
}
