package com.gotoubun.weddingvendor.service.payment;

import com.gotoubun.weddingvendor.data.payment.PaymentHistoryResponse;
import com.gotoubun.weddingvendor.data.payment.ReceiptDetailResponse;

import java.util.Collection;
import java.util.List;

public interface PaymentService {
    void save(String amount, String txnRef, String bankCode, String bankTransNo,
              String cardType, String orderInfo, String responseCode,
              String tmnCode, String transNo, String transStatus,
              String secureHash, String username, List<Long> serviceId);

    Collection<PaymentHistoryResponse> findAllReceipt(String username);

    ReceiptDetailResponse getReceiptDetail(Long id, String username);

}
