package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;

import java.util.Collection;

public interface SinglePostService {
    void save(SingleServicePostRequest singleServicePostRequest, String username);
    void update(Long id, SingleServicePostRequest singleServicePostRequest, String username);
    void delete(Long id, String username);
    SinglePostPagingResponse findAllSinglePost (int pageNo, int pageSize, String sortBy, String sortDir);
    Collection<SingleServicePostResponse> findAllSinglePost ();
    Collection<SingleServicePostResponse> findAllByVendors(String username);
    Collection<SingleServicePostResponse> findAllByCategories(Long categoryId);
    Collection<SingleServicePostResponse> findAllByCategoriesMyService(Long categoryId, String username);
}
