package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackagePostRequest {
    @NotBlank(message = "pack title should not be blank")
    String packTitle;
    @NotNull(message = "full name is not blank")
    Long packCategory;
    @NotBlank(message = "description should not be blank")
    String description;
    Collection<SinglePost> singlePosts;
}
