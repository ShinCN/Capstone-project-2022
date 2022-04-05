package com.gotoubun.weddingvendor.service.singlecategory.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.gotoubun.weddingvendor.service.singlecategory.SingleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.SingleCategoryRepository;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;

/**
 * The type Single category service.
 */
@Service
public class SingleCategoryServiceImpl implements SingleCategoryService {
	/**
	 * The Single category repository.
	 */
	@Autowired SingleCategoryRepository singleCategoryRepository;
	
//	@Override
//	public Page<SingleCategory> findAll(Pageable pageable, String searchText) {
//		// TODO Auto-generated method stub
//	    return singleCategoryRepository.findAllSingleCategorys(pageable, searchText);
//	}
//
//	@Override
//	public Page<SingleCategory> findAll(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return singleCategoryRepository.findAll(pageable);
//	}

	@Override
	public Collection<SingleCategory> findAll() {
		// TODO Auto-generated method stub
		return (Collection<SingleCategory>)singleCategoryRepository.findAll();
	}

	@Override
	public Optional<SingleCategory> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.of(singleCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category does not exist")));
	}


}
