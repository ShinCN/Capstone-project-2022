package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinglePostRepository  extends JpaRepository<SinglePost, Long> {
    @Override
    List<SinglePost> findAll();
//    SinglePost findBySinglePostIdentifier(String serviceId);



}
