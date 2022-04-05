package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KOL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KolRepository extends JpaRepository<KOL, Long> {
    KOL findByAccount(Account account);
}
