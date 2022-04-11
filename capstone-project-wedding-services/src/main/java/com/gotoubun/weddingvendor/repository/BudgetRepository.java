package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

}
