package com.gotoubun.weddingvendor.data.payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponse {
    LocalDateTime transactionTime;
    String TransactionInfo;
    int amount;
    String message;
    String transactionNumber;
    String bankCode;
}
