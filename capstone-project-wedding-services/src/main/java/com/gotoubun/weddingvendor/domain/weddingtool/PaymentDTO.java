package com.gotoubun.weddingvendor.domain.weddingtool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    Long singleServiceID;
    int amount;
    String paymentDescription;
    String bankCode;

}
