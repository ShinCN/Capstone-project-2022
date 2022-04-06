package com.gotoubun.weddingvendor.service.kol;

import com.gotoubun.weddingvendor.data.kol.KolRequest;
import com.gotoubun.weddingvendor.data.kol.KolUpdateNameRequest;
import com.gotoubun.weddingvendor.domain.user.KOL;

public interface KOLService {
    KOL save(KolRequest kolRequest);
    KOL updateName(KolUpdateNameRequest kolUpdateNameRequest);
    KOL updateEmail(KolUpdateNameRequest kolUpdateNameRequest);
    KOL update(KolUpdateNameRequest kolUpdateNameRequest);

}
