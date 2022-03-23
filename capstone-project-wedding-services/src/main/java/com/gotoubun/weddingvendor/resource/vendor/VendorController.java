package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotoubun.weddingvendor.resource.Resource;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.utils.ConstantUtils;

import java.util.Optional;

@RestController
@RequestMapping("/vendor")
public class VendorController  {
//	@Autowired
//	private IService<VendorProvider> vendorService;
//
//	@Autowired
//	private IPageService<VendorProvider> vendorPageService;
//
//
//	@Override
//	public ResponseEntity<Page<VendorProvider>> findAll(Pageable pageable, String searchText) {
//		// TODO Auto-generated method stub
//		return new ResponseEntity<>(vendorPageService.findAll(pageable, searchText), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<Page<VendorProvider>> findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
//		return new ResponseEntity<>(vendorPageService.findAll(PageRequest.of(pageNumber, pageSize
////						sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
//		)), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<?> findById(Long id) {
//		Optional<VendorProvider> vendor= vendorService.findById(id);
//		if(!vendor.isPresent()){
//			throw new ResourceNotFoundException(ConstantUtils.VENDORNOTFOUND.getMessage());
//		}
//		return ResponseEntity.ok().body(vendor);
//	}
//
//	@Override
//	public ResponseEntity<?> save(@Valid @RequestBody VendorProvider t) {
//		// TODO Auto-generated method stub
//		vendorService.saveOrUpdate(t);
//		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.ADDSUCCESS.getMessage()));
//	}
//
//	@Override
//	public ResponseEntity<?> update(VendorProvider t) {
//		// TODO Auto-generated method stub
//		vendorService.saveOrUpdate(t);
//		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.UPDATESUCCESS.getMessage()));
//	}
//
//	@Override
//	public ResponseEntity<?> deleteById(Long id) {
//		// TODO Auto-generated method stub
//		Optional<VendorProvider> vendor= vendorService.findById(id);
//		if(!vendor.isPresent()){
//			throw new ResourceNotFoundException(ConstantUtils.VENDORNOTFOUND.getMessage());
//		}
//		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.DELETESUCCESS.getMessage()));
//	}
}
