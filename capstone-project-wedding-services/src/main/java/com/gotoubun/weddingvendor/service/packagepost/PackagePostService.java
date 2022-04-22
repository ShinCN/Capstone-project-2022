package com.gotoubun.weddingvendor.service.packagepost;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

import java.util.Collection;
import java.util.List;

public interface PackagePostService {
    void save(PackagePostRequest packagePostNewRequest, String username);
    PackagePost update(Long id, PackagePostRequest packagePostNewRequest, String username);
    void updateSinglePost(Long id,Long singlePostId,String username);
    void deleteSinglePost(Long id,Long singlePostId,String username);
    Collection<SingleServicePostResponse> findByPackagePost(Long packageId);
    void delete(Long id,String username);
    List<PackagePostResponse> findAllPackPostByFilter( String keyWord, Long packageId, Float price);
}
