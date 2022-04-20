package com.gotoubun.weddingvendor.data.kol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * The type Kol request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KOLRequest {
    @NotBlank(message = "full name should not be blank")
    String fullName;
    @NotBlank(message = "email should not be blank")
    String email;
    @NotBlank(message = "phone should not be blank")
    String phone;
    @NotBlank(message = "address should not be blank")
    String address;
    String description;
}
