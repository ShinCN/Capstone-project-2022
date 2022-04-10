package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;

@Repository
public interface VendorRepository extends JpaRepository<VendorProvider, Long> {
//	@Query(value = "Select b FROM VendorProvider b WHERE b.address LIKE %:searchText% OR b.mail LIKE %:searchText% OR b.phone LIKE %:searchText%")
//	Page<VendorProvider> findAllVendors(Pageable pageable, @Param("searchText") String searchText);

	VendorProvider findByAccount(Account account);
	VendorProvider findByPhone(String phoneNumber);

}