package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


public interface GuestService {
    void save(GuestRequest guestRequest,Long guestListId,String username);
    void delete(String id,String username);
}
