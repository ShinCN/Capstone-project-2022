package com.gotoubun.weddingvendor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
@Repository
public interface SingleCategoryRepository extends JpaRepository<SingleCategory, Long>{
	 @Query(value = "Select b FROM SingleCategory b WHERE b.createdBy LIKE %:searchText% ")
	 Page<SingleCategory> findAllSingleCategorys(Pageable pageable, @Param("searchText") String searchText);


}
