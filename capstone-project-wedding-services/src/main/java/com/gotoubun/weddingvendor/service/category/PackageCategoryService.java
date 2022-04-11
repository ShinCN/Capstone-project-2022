package com.gotoubun.weddingvendor.service.category;

import com.gotoubun.weddingvendor.data.category.CategoryResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;

import java.util.Collection;
import java.util.Optional;

public interface PackageCategoryService {
    Collection<CategoryResponse> findAll();
    Optional<PackageCategory> findById(Long id);
}
