package com.gotoubun.weddingvendor.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@Data
public class SingleServicePostNameRequest {
    @NotBlank(message = "service name field must not be blank")
    String serviceName;

}