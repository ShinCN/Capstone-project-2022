package com.gotoubun.weddingvendor.data.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckListTaskResponse {
    String id;
    String name;
    LocalDate dueDate;
    LocalDateTime createdDate;
    boolean status;
}
