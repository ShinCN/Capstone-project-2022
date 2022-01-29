package com.gotoubun.weddingvendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

}
