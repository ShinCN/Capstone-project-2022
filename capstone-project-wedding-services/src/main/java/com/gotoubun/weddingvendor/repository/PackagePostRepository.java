package com.gotoubun.weddingvendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotoubun.weddingvendor.entity.vendor.PackagePost;
@Repository
public interface PackagePostRepository extends JpaRepository<PackagePost, String>{

}
