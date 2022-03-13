package com.gotoubun.weddingvendor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.entity.user.VendorProvider;

@Repository
public interface VendorRepository extends JpaRepository<VendorProvider, Long> {
	@Query(value = "Select b FROM VendorProvider b WHERE b.address LIKE %:searchText% OR b.mail LIKE %:searchText% OR b.phone LIKE %:searchText% OR b.username LIKE %:searchText%")
	Page<VendorProvider> findAllVendors(Pageable pageable, @Param("searchText") String searchText);

}
