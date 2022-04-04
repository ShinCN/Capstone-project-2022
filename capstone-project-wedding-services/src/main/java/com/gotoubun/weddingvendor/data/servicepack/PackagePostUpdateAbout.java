package com.gotoubun.weddingvendor.data.servicepack;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class PackagePostUpdateAbout {
    @NotBlank(message = "about field must not be blank")
    String about;
}
