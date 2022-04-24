package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.Price;
import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.Collection;
import java.util.List;

public interface SinglePostService {
    void save(SingleServicePostRequest singleServicePostRequest, String username);

    void update(Long id, SingleServicePostRequest singleServicePostRequest, String username);

    void delete(Long id, String username);

    SingleServicePostResponse load(Long singlePostId);

    SinglePostPagingResponse findAllSinglePost(int pageNo, int pageSize, String sortBy, String sortDir);

    Collection<SingleServicePostResponse> filterSingleService(String scope,Long categoryId,String keyWord);

    Collection<SingleServicePostResponse> filterSingleServiceByPrice(Price price);

    Collection<SingleServicePostResponse> findAllSinglePost();

    Collection<SingleServicePostResponse> findAllByVendors(String username);

    Collection<SingleServicePostResponse> findAllByCategories(Long categoryId);

    Collection<SingleServicePostResponse> findAllByCustomer(String username);
}
