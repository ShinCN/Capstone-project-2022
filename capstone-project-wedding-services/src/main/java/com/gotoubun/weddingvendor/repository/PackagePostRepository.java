package com.gotoubun.weddingvendor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.domain.vendor.PackagePost;

import java.util.List;

@Repository
public interface PackagePostRepository extends PagingAndSortingRepository<PackagePost, Long> {
    @Query("FROM PackagePost p WHERE p.serviceName LIKE %:keyWord%")
    Page<PackagePost> findAllPackagePost(Pageable pageable, @Param("keyWord") String keyWord);

    @Query("FROM PackagePost p WHERE p.serviceName LIKE %:keyWord%")
    List<PackagePost> filterPackagePostByServiceName(@Param("keyWord") String keyWord);

}
