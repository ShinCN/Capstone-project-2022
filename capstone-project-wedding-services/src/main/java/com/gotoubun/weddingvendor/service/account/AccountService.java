package com.gotoubun.weddingvendor.service.account;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findByUserName(String username);
    Account updateStatus(Long id, AccountStatusRequest accountStatusRequest);
    int getRole(String username);
    int getStatus(String username);
    Collection<VendorProviderResponse> findAllVendor();
    Collection<KOLResponse> findAllKOL();
    void updatePassword(AccountPasswordRequest passWord,String username);

}
