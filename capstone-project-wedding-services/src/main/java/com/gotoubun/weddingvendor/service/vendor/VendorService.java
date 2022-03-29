package com.gotoubun.weddingvendor.service.vendor;

import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.data.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

public interface VendorService {
    VendorProvider save(VendorProviderRequest vendorProviderRequest);
    VendorProvider update(VendorProviderRequest vendorProviderRequest);
    SinglePost saveSingleServicePost(SingleServicePostNewRequest singleServicePostRequest, String username);
    SinglePost updateSingleServicePost(SingleServicePostUpdateRequest singleServicePostRequest, String username);
    void deleteSingleServicePost(Long id);

}
