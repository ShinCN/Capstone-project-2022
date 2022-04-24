package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.feedback.FeedBackRequest;
import com.gotoubun.weddingvendor.data.feedback.FeedBackResponse;

import java.util.Collection;

public interface FeedBackService {
    void save(Long receiptId, Long serviceId, FeedBackRequest request, String username);
    Collection<FeedBackResponse> findGoodRateFeedBack();
}
