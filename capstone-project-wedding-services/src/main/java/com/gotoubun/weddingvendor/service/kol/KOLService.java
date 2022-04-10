package com.gotoubun.weddingvendor.service.kol;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;

public interface KOLService {
    KeyOpinionLeader save(KOLRequest kolRequest);
    KeyOpinionLeader update(KOLRequest kolRequest, String username);
}
