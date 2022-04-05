package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.gotoubun.weddingvendor.resource.Resource;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.utils.ConstantUtils;

import java.security.Principal;
import java.util.Optional;

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;

/**
 * The type Vendor controller.
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    public ResponseEntity<?> findById(Long id) {
        return null;
    }

    /**
     * Post vendor response entity.
     *
     * @param vendorProviderRequest the vendor provider request
     * @param bindingResult         the binding result
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postVendor(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult) {
        // TODO Auto-generated method stub
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

         vendorService.save(vendorProviderRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }





}
