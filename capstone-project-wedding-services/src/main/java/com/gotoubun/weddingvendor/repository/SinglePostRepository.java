package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinglePostRepository  extends JpaRepository<SinglePost, Long> {
    @Query("FROM SinglePost s WHERE s.discardedDate IS NULL")
    Page<SinglePost> findAllSinglePost(Pageable pageable);
    List<SinglePost> findAllByVendorProvider(VendorProvider vendorProvider);
    List<SinglePost> findAllBySingleCategory(SingleCategory singleCategory);
    List<SinglePost> findAllByPackagePosts(PackagePost packagePost);
    List<SinglePost> findAllBySingleCategoryAndCustomers(SingleCategory singleCategory, Customer customer);
    List<SinglePost> findAllByCustomers(Customer customer);

    @Query("FROM SinglePost s WHERE s.price BETWEEN :from AND :to")
    List<SinglePost> filterSingleServiceBetween(@Param("from") Float from , @Param("to") Float to);

    @Query("FROM SinglePost s WHERE s.price > :value")
    List<SinglePost> filterSingleServiceGreaterThan(@Param("value") Float value);

    @Query("FROM SinglePost s WHERE s.price < :value")
    List<SinglePost> filterSingleServiceLessThan(@Param("value") Float value);

    @Query("FROM SinglePost s WHERE s.serviceName LIKE %:keyword% or s.about LIKE %:keyword%" )
    List<SinglePost> filterSingleServiceByTitleOrServiceName(@Param("keyword") String keyword);

    @Override
    Optional<SinglePost> findById(Long aLong);
    //    SinglePost findBySinglePostIdentifier(String serviceId);
}

