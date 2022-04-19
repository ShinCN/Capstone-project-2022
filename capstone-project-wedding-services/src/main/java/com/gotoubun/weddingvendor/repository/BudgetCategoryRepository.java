package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,Long> {

    Optional<BudgetCategory> findByCategoryName(String categoryName);
    List<BudgetCategory> findAllByBudget_Customer_Account(String username);
}
