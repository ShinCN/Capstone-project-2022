package com.gotoubun.weddingvendor.data.singleservice;

import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SinglePostPagingResponse {
    Collection<SingleServicePostResponse> singleServicePostResponses;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
