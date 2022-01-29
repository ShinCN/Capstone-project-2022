package com.gotoubun.weddingvendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.entity.account.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
