package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.budget.BudgetCategoryRequest;
import com.gotoubun.weddingvendor.data.budget.BudgetResponse;
import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;

import java.util.Collection;

public interface BudgetCategoryService {
    void save (BudgetCategoryRequest budgetCategoryRequest, String username);
    BudgetCategory update (Long id, BudgetCategoryRequest budgetCategoryRequest, String username);
    Collection<BudgetResponse> findAllByBudget_Customer_Account(String username);
    void delete(Long id);
}
