package com.gotoubun.weddingvendor.service.kol;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;

public interface KOLService {
    void save(KOLRequest kolRequest);
    void update(KOLRequest kolRequest, String username);
    KOLResponse load(String username);
}
