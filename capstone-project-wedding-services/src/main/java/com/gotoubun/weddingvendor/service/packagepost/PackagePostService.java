package com.gotoubun.weddingvendor.service.packagepost;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostPagingResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

import java.util.Collection;
import java.util.List;

/**
 * The interface Package post service.
 */
public interface PackagePostService {
    PackagePost save(PackagePostRequest packagePostNewRequest, String username);

    void update(Long id, PackagePostRequest packagePostNewRequest, String username);

    void delete(Long id, String username);

    void updateSinglePost(Long id, Long singlePostId, String username);

    void deleteSinglePost(Long id, Long singlePostId, String username);

    PackagePostResponse load(Long packageId, String username);

    List<SingleServicePostResponse> findAllSingleServiceByPackagePostAndSingleCategory(Long packageId,Long categoryId);

    List<PackagePostResponse> findAllPackagePostByFilter(String keyWord, Long packageId, Float price);

    List<PackagePostResponse> findAllPackagePostByKeyOpinionLeader(String username);

    PackagePostPagingResponse findAllPackagePost(int pageNo, int pageSize, String sortBy, String sortDir);

    PackagePostResponse convertToResponse(PackagePost packagePost);
}
