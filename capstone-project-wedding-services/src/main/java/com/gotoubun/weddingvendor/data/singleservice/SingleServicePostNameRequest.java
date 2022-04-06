package com.gotoubun.weddingvendor.data.singleservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class SingleServicePostNameRequest {
    @NotBlank(message = "service name field must not be blank")
    @JsonProperty("service name")
    String serviceName;
}
