package com.gotoubun.weddingvendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.entity.user.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
