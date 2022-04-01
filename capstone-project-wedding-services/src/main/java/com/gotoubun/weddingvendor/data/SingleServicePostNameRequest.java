package com.gotoubun.weddingvendor.data;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class SingleServicePostNameRequest {
    @NotBlank(message = "service name field must not be blank")
    String serviceName;
}