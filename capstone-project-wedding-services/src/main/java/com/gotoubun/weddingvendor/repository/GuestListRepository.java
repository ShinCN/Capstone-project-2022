package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestListRepository extends JpaRepository<GuestList, Long > {
    GuestList findByCustomer(Customer customer);
}
