package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.service.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.gotoubun.weddingvendor.message.MessageToUser;

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;

    public ResponseEntity<?> findById(Long id) {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult) {
        // TODO Auto-generated method stub
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        vendorService.save(vendorProviderRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

//    @PostMapping("/update")
//    public ResponseEntity<?> update(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult) {
//        // TODO Auto-generated method stub
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
//        if (errorMap != null) return errorMap;
//
//        vendorService.update(vendorProviderRequest);
//
//        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
//    }
}