package com.gotoubun.weddingvendor.service.account;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLPagingResponse;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findByUserName(String username);
    void updatePassword(AccountPasswordRequest passWord,String username);
    int getStatus(String username);
    int getRole(String username);
}
