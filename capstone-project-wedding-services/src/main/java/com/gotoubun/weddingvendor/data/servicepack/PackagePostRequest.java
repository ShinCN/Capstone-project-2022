package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackagePostRequest {
    @NotBlank(message = "pack title field must not be blank")
    String packTitle;
    Float price;
    @NotBlank(message = "about field must not be blank")
    String description;
    @NotEmpty(message = "services must be added")
    Collection<SinglePost> singlePosts;

}
