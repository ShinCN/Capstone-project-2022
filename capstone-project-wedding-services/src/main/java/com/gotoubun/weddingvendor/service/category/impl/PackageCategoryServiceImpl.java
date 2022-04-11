package com.gotoubun.weddingvendor.service.category.impl;

import com.gotoubun.weddingvendor.data.category.CategoryResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.PackageCategoryRepository;
import com.gotoubun.weddingvendor.service.category.PackageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageCategoryServiceImpl implements PackageCategoryService {
    @Autowired
    PackageCategoryRepository packageCategoryRepository;


    @Override
    public Collection<CategoryResponse> findAll() {
        List<PackageCategory> categories = packageCategoryRepository.findAll();
        return categories.stream().map(c ->
                new CategoryResponse(c.getId(), c.getPackageName())
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<PackageCategory> findById(Long id) {
        // TODO Auto-generated method stub
        return Optional.of(packageCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category does not exist")));
    }

}
