package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository   extends JpaRepository<Guest, Long> {
}
