package com.gotoubun.weddingvendor.service.category;

import com.gotoubun.weddingvendor.data.category.CategoryResponse;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import java.util.Collection;
import java.util.Optional;

public interface SingleCategoryService {
    Collection<CategoryResponse> findAll();
    Optional<SingleCategory> findById(Long id);
}
