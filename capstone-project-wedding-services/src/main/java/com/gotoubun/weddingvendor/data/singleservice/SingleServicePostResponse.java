package com.gotoubun.weddingvendor.data.singleservice;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleServicePostResponse {
    Long id;
    String serviceName;
    Float price;
    String description;
    Collection<PhotoResponse> photos;
}
