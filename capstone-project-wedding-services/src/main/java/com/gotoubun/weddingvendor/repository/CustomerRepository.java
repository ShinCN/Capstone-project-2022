package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.domain.user.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByAccount(Account account);
    Customer findByPhone(String phone);
}
