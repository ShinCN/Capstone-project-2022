package com.gotoubun.weddingvendor.data.payment;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotEmpty(message = "Hiring services must not be empty")
    Collection<SinglePost> singlePosts;
    @NotNull(message = "Money amount must not be null")
    int amount;
    String paymentDescription;
    @NotBlank(message = "bank code field must not be blank")
    String bankCode;

}
