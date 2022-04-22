package com.gotoubun.weddingvendor.data.guest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestListResponse {
    Long id;
    String name;
    List<GuestResponse> guestResponses;
}
