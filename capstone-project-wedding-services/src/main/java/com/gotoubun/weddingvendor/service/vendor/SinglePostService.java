package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;

import java.util.Collection;

public interface SinglePostService {
    void save(SingleServicePostRequest singleServicePostRequest, String username);
    void update(Long id, SingleServicePostRequest singleServicePostRequest, String username);
    void delete(Long id);
    Collection<SingleServicePostResponse> findAllByVendors(Long id);
    Collection<SingleServicePostResponse> findAllByCategories(Long categoryId);
    Collection<SingleServicePostResponse> findAll();
    Collection<SingleServicePostResponse> findAllByCategoriesMyService(Long categoryId, String username);
}
