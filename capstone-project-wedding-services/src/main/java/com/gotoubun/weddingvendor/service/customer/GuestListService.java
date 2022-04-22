package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.guest.GuestListResponse;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;

import java.util.Collection;

public interface GuestListService {
    void save(GuestListRequest guestListRequest,String username);
    void update(String guestListName,String username,Long id);
    void delete(Long id,String username);
    Collection<GuestListResponse> findAllGuestList(String username);
}
