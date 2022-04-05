package com.gotoubun.weddingvendor.service.singlecategory;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import java.util.Collection;
import java.util.Optional;

public interface SingleCategoryService {
    Collection<SingleCategory> findAll();
    Optional<SingleCategory> findById(Long id);
}
