package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.Collection;

public interface SinglePostService {
    void save(SingleServicePostRequest singleServicePostRequest, String username);
    void update(Long id, SingleServicePostRequest singleServicePostRequest, String username);
    void delete(Long id);
    SinglePostPagingResponse findAllSinglePost (int pageNo, int pageSize, String sortBy, String sortDir);
    Collection<SingleServicePostResponse> findAllByVendors(Long id);
    Collection<SingleServicePostResponse> findAllByCategories(Long categoryId);
}
