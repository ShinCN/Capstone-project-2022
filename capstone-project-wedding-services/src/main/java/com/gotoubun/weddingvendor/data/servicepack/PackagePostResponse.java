package com.gotoubun.weddingvendor.data.servicepack;

import com.gotoubun.weddingvendor.data.kol.KOLMiniResponse;
import com.gotoubun.weddingvendor.data.singleservice.PhotoResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PackagePostResponse {
     Long id;
     String name;
     float rate;
     float price;
     String description;
     PhotoResponse photo;
     KOLMiniResponse kolMiniResponse;
}
