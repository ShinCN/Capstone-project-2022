package com.gotoubun.weddingvendor.service.admin;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLPagingResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;

import java.util.Collection;

public interface AdminService {
    void updateStatus(Long id, AccountStatusRequest accountStatusRequest);
    KOLPagingResponse findAllKOL(int pageNo, int pageSize, String sortBy, String sortDir);
    Collection<VendorProviderResponse> findAllVendor();
}
