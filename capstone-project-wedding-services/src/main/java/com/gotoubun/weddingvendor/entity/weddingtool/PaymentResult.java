package com.gotoubun.weddingvendor.entity.weddingtool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    private String status;
    private String message;
    private String url;
}
