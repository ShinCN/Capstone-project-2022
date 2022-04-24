package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllBySinglePost(SinglePost singlePost);
}
