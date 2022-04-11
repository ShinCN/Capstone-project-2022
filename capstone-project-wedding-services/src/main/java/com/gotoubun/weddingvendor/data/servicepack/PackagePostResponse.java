package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.data.kol.KOLMiniResponse;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PackagePostResponse {
     Long id;
     String name;
     int rate;
     String imgUrl;
     KOLMiniResponse kolMiniResponse;

}
