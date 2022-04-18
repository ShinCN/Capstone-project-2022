package com.gotoubun.weddingvendor.data.checklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListTaskRequest {
    String name;
    String deadline;
    String description;
}
