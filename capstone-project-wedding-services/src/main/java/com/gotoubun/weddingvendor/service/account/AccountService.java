package com.gotoubun.weddingvendor.service.account;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findByUserName(String username);
    Account updateStatus(AccountStatusRequest accountStatusRequest, String username);
    int getRole(String username);
}
