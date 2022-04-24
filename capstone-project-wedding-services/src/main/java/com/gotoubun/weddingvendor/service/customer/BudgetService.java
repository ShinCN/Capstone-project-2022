package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.weddingtool.Budget;

public interface BudgetService {
    Budget findByCustomerAccount(Account account);
}