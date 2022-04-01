package com.gotoubun.weddingvendor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    Long singleServiceID;
    int amount;
    String paymentDescription;
    String bankCode;

}
