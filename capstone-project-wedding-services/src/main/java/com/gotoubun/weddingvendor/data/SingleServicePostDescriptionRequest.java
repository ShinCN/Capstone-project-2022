package com.gotoubun.weddingvendor.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@NoArgsConstructor
@Data
public class SingleServicePostDescriptionRequest {
    @JsonProperty("description")
    String description;
}
