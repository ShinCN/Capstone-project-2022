package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;

public interface GuestListService {
    void save(GuestListRequest guestListRequest,String username);
    void update(String guestListName,String username,Long id);
    void delete(Long id,String username);
}
