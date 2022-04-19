package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    Collection<PaymentHistory> findAllByCustomer_Account(Account account);

}
