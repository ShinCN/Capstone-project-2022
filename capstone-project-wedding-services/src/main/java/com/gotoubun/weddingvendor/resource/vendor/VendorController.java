package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotoubun.weddingvendor.resource.Resource;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.utils.ConstantUtils;

import java.security.Principal;
import java.util.Optional;

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    public ResponseEntity<?> findById(Long id) {
        return null;
    }


    public ResponseEntity<?> register(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult) {
        // TODO Auto-generated method stub
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        VendorProvider newVendor = vendorService.save(vendorProviderRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    @PostMapping("/single-category")
    public ResponseEntity<?> save(@Valid @RequestBody SingleServicePostNewRequest singleServicePost, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = vendorService.saveSingleServicePost(singleServicePost,principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }



    public ResponseEntity<?> update(VendorProviderRequest vendorProviderRequest) {
        return null;
    }


    public ResponseEntity<?> deleteById(Long id) {
        // TODO Auto-generated method stub
//		Optional<VendorProvider> vendor= vendorService.findById(id);
//		if(!vendor.isPresent()){
//			throw new ResourceNotFoundException(ConstantUtils.VENDORNOTFOUND.getMessage());
//		}
//		return ResponseEntity.ok().body(new MessageToUser(ConstantUtils.DELETESUCCESS.getMessage()));
        return null;
    }
}
