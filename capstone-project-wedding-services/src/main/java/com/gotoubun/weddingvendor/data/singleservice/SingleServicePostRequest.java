package com.gotoubun.weddingvendor.data.singleservice;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SingleServicePostRequest {
    @NotBlank(message = "service name is required")
    String serviceName;
    @NotNull
    Float price;
    String description;
    Collection<PhotoRequest> photos;
}
