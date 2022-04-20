package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;
import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


public interface GuestService {
    void save(GuestRequest guestRequest, String username, Long guestListId);

    void update(GuestRequest guestRequest, String username, Long guestListId, String id);

    void delete(String id, String username);

    Collection<GuestResponse> findAllGuest(Long guestListId);
}
