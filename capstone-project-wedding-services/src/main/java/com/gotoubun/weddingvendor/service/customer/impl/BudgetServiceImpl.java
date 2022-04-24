package com.gotoubun.weddingvendor.service.customer.impl;


import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;
import com.gotoubun.weddingvendor.repository.BudgetRepository;
import com.gotoubun.weddingvendor.service.customer.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget findByCustomerAccount(Account account) {
        Budget budget = budgetRepository.findByCustomerAccount(account);
        return budget;
    }


}