package com.gotoubun.weddingvendor.data.budget;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetCategoryRequest {
    @NotBlank(message = "category name must not be blank")
    String categoryName;
    Long cost;
}
