package com.gotoubun.weddingvendor.data.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReceiptDetailResponse {
    LocalDateTime createdDate;
    String TxnRef;
    String paymentMethod;
    Float totalPrice;
    Collection<SinglePostPaymentResponse> singlePostPaymentResponses;
}
