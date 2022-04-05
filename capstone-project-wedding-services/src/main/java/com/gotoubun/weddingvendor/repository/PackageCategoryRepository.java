package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageCategoryRepository extends JpaRepository<PackageCategory, Long> {
    @Query(nativeQuery=true, value = "Select b FROM PackageCategory b WHERE b.createdby LIKE %:searchText% ")
    Page<SingleCategory> findAllSingleCategorys(Pageable pageable, @Param("searchText") String searchText);

    @Query(nativeQuery=true, value = "Select  FROM PackageCategory b WHERE b.category_name LIKE %:searchText% ")
    Optional<SingleCategory> findByName(@Param("searchText") String searchText);
}
