package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;

public interface VendorService {
    void save(VendorProviderRequest vendorProviderRequest);
    void update(VendorProviderRequest vendorProviderRequest,String username);
    VendorProviderResponse load(String username);
}
