package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.weddingtool.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
