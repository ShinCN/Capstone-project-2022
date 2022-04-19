package com.gotoubun.weddingvendor.data.budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BudgetResponse {
    Long id;
    String categoryName;
    Long cost;
}
