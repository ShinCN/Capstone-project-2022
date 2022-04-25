package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
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
public interface PackagePostRepository extends JpaRepository<PackagePost, Long> {
    @Query("FROM PackagePost p WHERE p.discardedDate IS NULL")
    Page<PackagePost> findAllPackagePost(Pageable pageable);

    @Query("FROM PackagePost p WHERE p.discardedDate IS NULL")
    List<PackagePost> findAllPackagePost();

    @Query("FROM PackagePost p WHERE p.discardedDate IS NULL and p.keyOpinionLeader.id = :kolId")
    List<PackagePost> findAllPackagePostByKeyOpinionLeader(Long kolId);

    @Query("FROM PackagePost p WHERE p.serviceName LIKE %:keyWord%")
    List<PackagePost> filterPackagePostByServiceName(@Param("keyWord") String keyWord);

    List<PackagePost> findAllByCustomers(Customer customer);

    Page<PackagePost> findAll(Pageable pageable);

    List<PackagePost> findAllBySinglePosts(SinglePost singlePost);

}
