package com.gotoubun.weddingvendor.data.servicepack;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class PackagePostUpdateTitleRequest {
    @NotBlank(message = "pack title field must not be blank")
    String serviceTitle;
}
