package com.gotoubun.weddingvendor.service.kol;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.KOL;

public interface KOLService {
    KOL save(KOLRequest kolRequest);

}
