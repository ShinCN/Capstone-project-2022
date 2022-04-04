package com.gotoubun.weddingvendor.data.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    Long singleServiceID;
    int amount;
    String paymentDescription;
    @NotBlank(message = "bank code field must not be blank")
    String bankCode;

}
