package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.SingleServicePostNameRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.Collection;

public interface SinglePostService {
    void save(SingleServicePostNewRequest singleServicePostRequest, String username);
    SinglePost updateName(Long id, String serviceName, String username);
    SinglePost updatePrice(Long id, Float price, String username);
    SinglePost updateDescription(Long id, String description, String username);
    SinglePost updatePhotos(Long id, Collection<Photo> photos, String username);
    void delete(Long id);
}
