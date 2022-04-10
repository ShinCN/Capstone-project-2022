package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.Collection;

public interface SinglePostService {
    void save(SingleServicePostRequest singleServicePostRequest, String username);
    void update(Long id, SingleServicePostRequest singleServicePostRequest, String username);
    SinglePost updateName(Long id, String serviceName, String username);
    SinglePost updatePrice(Long id, Float price, String username);
    SinglePost updateDescription(Long id, String description, String username);
    SinglePost updatePhotos(Long id, Collection<Photo> photos, String username);
    void delete(Long id);
    Collection<SingleServicePostResponse> findAllByVendors(String username);
    Collection<SingleServicePostResponse> findAll();
}
