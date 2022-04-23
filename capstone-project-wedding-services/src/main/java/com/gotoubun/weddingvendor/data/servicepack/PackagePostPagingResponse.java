package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackagePostPagingResponse {
    Collection<PackagePostResponse> packagePostResponses;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
