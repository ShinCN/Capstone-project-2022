package com.gotoubun.weddingvendor.data.budget;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetRequest {
    String categoryName;
    Long cost;
}
