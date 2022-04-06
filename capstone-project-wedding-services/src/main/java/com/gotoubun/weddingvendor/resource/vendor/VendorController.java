package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.gotoubun.weddingvendor.message.MessageToUser;
import java.security.Principal;
import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;
import static com.gotoubun.weddingvendor.resource.MessageConstant.UPDATE_SUCCESS;

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
    @PostMapping("/register")
    public ResponseEntity<?> postVendor(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult) {
        // TODO Auto-generated method stub
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        vendorService.save(vendorProviderRequest);
        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        vendorService.update(vendorProviderRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }
}

