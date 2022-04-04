package com.gotoubun.weddingvendor.service;

import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

public interface SinglePostService {
    SinglePost save(SingleServicePostNewRequest singleServicePostRequest, String username);
    SinglePost updateName(Long id, String serviceName, String username);
    void delete(Long id);
}