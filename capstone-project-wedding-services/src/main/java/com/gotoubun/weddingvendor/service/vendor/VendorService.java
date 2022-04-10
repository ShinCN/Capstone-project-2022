package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;

public interface VendorService {
    VendorProvider save(VendorProviderRequest vendorProviderRequest);
    VendorProvider update(VendorProviderRequest vendorProviderRequest);
    VendorProviderResponse getByUsername(String username);
}
