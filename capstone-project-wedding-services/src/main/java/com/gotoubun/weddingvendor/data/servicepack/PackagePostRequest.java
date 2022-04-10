package com.gotoubun.weddingvendor.data.servicepack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackagePostRequest {
    @NotBlank(message = "pack title should not be blank")
    String packTitle;

    @NotBlank(message = "description should not be blank")
    String description;

    @NotNull(message = "you have to choose theme for your service")
    Long packCategoryId;


}
