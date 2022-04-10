package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KolRepository extends JpaRepository<KeyOpinionLeader, Long> {
    KeyOpinionLeader findByAccount(Account account);
    KeyOpinionLeader findByPhone(String phoneNumber);

}
