package com.gotoubun.weddingvendor.service;

import com.gotoubun.weddingvendor.data.SingleServicePostNameRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import org.springframework.stereotype.Service;

public interface SinglePostService {
    SinglePost save(SingleServicePostNewRequest singleServicePostRequest, String username);
    SinglePost updateName(Long id, String serviceName, String username);
    void delete(Long id);
}