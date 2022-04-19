package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Optional<Customer> findByAccount(Optional<Account> account);
    Customer findByPhone(String phone);
}
