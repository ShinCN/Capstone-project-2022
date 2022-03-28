package com.gotoubun.weddingvendor.service.impl.vendor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.utils.ConstantUtils;

@Service
public class VendorServiceImpl implements  IService<VendorProvider>, IPageService<VendorProvider> {

	@Autowired
	VendorRepository vendorRepository;

	@Override
	public Page<VendorProvider> findAll(Pageable pageable, String searchText) {
		// TODO Auto-generated method stub
		return vendorRepository.findAllVendors(pageable, searchText);
	}

	@Override
	public Page<VendorProvider> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return vendorRepository.findAll(pageable);
		
	}

	@Override
	public Optional<VendorProvider> findById(Long id) {
		// TODO Auto-generated method stub
		 return Optional.ofNullable(vendorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ConstantUtils.IDNOTESIST.getMessage()+id)));
	}
//	public Optional<VendorProvider> findByPhone(String phone) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public VendorProvider saveOrUpdate(VendorProvider vendor) {
		// TODO Auto-generated method stub
		return vendorRepository.save(vendor);
	}

	@Override
	public void deleteById(Long id) {
		if(!findById(id).isPresent())
		{
			throw new ResourceNotFoundException("Id is not exist"+id);
		}
		vendorRepository.deleteById(id); 
	}

	@Override
	public List<VendorProvider> saveAll(List<VendorProvider> t) {
		return null;
	}

	@Override
	public Collection<VendorProvider> findAll() {
		// TODO Auto-generated method stub
		return (Collection<VendorProvider>) vendorRepository.findAll();
	}

}
