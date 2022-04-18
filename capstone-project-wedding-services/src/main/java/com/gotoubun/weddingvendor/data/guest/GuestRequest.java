package com.gotoubun.weddingvendor.data.guest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequest {
    @Nullable
    String id;
    @NotBlank(message = "Name is not blank")
    String fullName;
    String phone;
    String mail;
    String address;

}
