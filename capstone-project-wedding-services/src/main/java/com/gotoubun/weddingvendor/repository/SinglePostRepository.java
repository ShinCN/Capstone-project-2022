package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinglePostRepository  extends JpaRepository<SinglePost, Long> {
    List<SinglePost> findAll();
    List<SinglePost> findAllByVendorProvider(VendorProvider vendorProvider);
    List<SinglePost> findAllBySingleCategory(SingleCategory singleCategory);
    List<SinglePost> findAllByPackagePosts(PackagePost packagePost);
    List<SinglePost> findAllBySingleCategoryAndCustomers(SingleCategory singleCategory, Customer customer);

    @Override
    Optional<SinglePost> findById(Long aLong);
    //    SinglePost findBySinglePostIdentifier(String serviceId);
}

