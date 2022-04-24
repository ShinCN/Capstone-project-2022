package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.vendor.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllBySinglePostId(Long id);
    List<Feedback> findAllBySinglePostRate(Float rate);
}
