package com.gotoubun.weddingvendor.data.vendorprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorProviderPagingResponse {
    Collection<VendorProviderResponse> vendorProviderResponses;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
