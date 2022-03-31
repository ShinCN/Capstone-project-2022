package com.gotoubun.weddingvendor.service.account;

import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findByUserName(String username);
    int getRole(String username);
}
