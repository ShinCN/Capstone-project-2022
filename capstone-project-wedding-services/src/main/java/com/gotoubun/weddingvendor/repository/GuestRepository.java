package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository   extends JpaRepository<Guest, String> {
    List<Guest> findByGuestList(GuestList guestList);
}
