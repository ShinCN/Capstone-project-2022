package com.gotoubun.weddingvendor.controller.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gotoubun.weddingvendor.controller.Resource;
import com.gotoubun.weddingvendor.entity.user.VendorProvider;
import com.gotoubun.weddingvendor.entity.vendor.SingleCategory;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.repository.SingleCategoryRepository;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.utils.ConstantUtils;

public class SingleCategoryController implements Resource<SingleCategory> {
	@Autowired
	private IService<SingleCategory> singleCatetoryService;

	@Autowired
	private IPageService<SingleCategory>  singleCatetoryPageService;

	@Override
	public ResponseEntity<Page<SingleCategory>> findAll(Pageable pageable, String searchText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Page<SingleCategory>> findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(singleCatetoryPageService.findAll(PageRequest.of(pageNumber, pageSize
//				sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
		)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findById(Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok().body(singleCatetoryService.findById(id).get());
	}

	@Override
	public ResponseEntity<?> save(SingleCategory t) {
		// TODO Auto-generated method stub
		singleCatetoryService.saveOrUpdate(t);
		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.ADDSUCCESS.getMessage()));
	}

	@Override
	public ResponseEntity<?> update(SingleCategory t) {
		singleCatetoryService.saveOrUpdate(t);
		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.UPDATESUCCESS.getMessage()));
	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {
		// TODO Auto-generated method stub
		singleCatetoryService.deleteById(id);
		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.DELETESUCCESS.getMessage()));
	}

}
