package com.gotoubun.weddingvendor.service.category.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gotoubun.weddingvendor.data.category.CategoryResponse;
import com.gotoubun.weddingvendor.service.category.SingleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.SingleCategoryRepository;

/**
 * The type Single category service.
 */
@Service
public class SingleCategoryServiceImpl implements SingleCategoryService {
    /**
     * The Single category repository.
     */
    @Autowired
    SingleCategoryRepository singleCategoryRepository;


    @Override
    public Collection<CategoryResponse> findAll() {
        // TODO Auto-generated method stub
        Collection<SingleCategory> categories = singleCategoryRepository.findAll();
        return categories.stream().map(c ->
                new CategoryResponse(c.getId(), c.getCategoryName())
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<SingleCategory> findById(Long id) {
        // TODO Auto-generated method stub
        return Optional.of(singleCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category does not exist")));
    }


}
