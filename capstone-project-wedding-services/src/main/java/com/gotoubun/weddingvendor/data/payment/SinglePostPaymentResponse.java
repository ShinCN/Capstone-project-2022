package com.gotoubun.weddingvendor.data.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor@Data
@Builder
public class SinglePostPaymentResponse {
    Long serviceId;
    String serviceName;
    Float amount;
}
