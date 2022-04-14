package com.gotoubun.weddingvendor.data.guest;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Data
public class GuestListRequest {
    @NotBlank(message = "name is required")
    String name;
}
