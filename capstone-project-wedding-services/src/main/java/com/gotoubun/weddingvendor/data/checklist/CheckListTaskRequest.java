package com.gotoubun.weddingvendor.data.checklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListTaskRequest {
    String name;
    @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}",message = "Please enter correct date format")
    String deadline;

}
