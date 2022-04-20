package com.gotoubun.weddingvendor.data.guest;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Data
public class GuestListRequest {
    @NotBlank(message = "guest list name is not blank")
    String name;
}
