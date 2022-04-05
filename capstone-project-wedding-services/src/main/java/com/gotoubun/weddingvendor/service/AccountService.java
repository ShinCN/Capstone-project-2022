package com.gotoubun.weddingvendor.service;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Account updateStatus(AccountStatusRequest accountStatusRequest, String username);
    Optional<Account> findByUserName(String username);
    int getRole(String username);
}