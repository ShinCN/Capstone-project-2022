package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.feedback.FeedBackRequest;

public interface FeedBackService {
    void save(Long receiptId, Long serviceId, FeedBackRequest request, String username);
}
