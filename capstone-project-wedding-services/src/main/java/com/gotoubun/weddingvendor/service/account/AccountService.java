package com.gotoubun.weddingvendor.service.account;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.domain.user.Account;

public interface AccountService {
    Account save(Account account);
    Account findByUserName(String username);
    void updatePassword(AccountPasswordRequest passWord,String username);
    boolean getStatus(String username);
    int getRole(String username);
    String getFullName(Account account);
}
