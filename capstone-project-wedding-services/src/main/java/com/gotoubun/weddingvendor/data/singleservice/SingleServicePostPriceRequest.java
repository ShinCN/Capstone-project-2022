package com.gotoubun.weddingvendor.data.singleservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class SingleServicePostPriceRequest {
    @NotNull
    @JsonProperty("price")
    Float price;
}
