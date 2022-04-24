package com.gotoubun.weddingvendor.resource.vendor;

import javax.validation.Valid;

import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderRequest;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
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

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;

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

    @PutMapping
    public ResponseEntity<?> putVendor(@Valid @RequestBody VendorProviderRequest vendorProviderRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        vendorService.update(vendorProviderRequest,principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<VendorProviderResponse> getVendorProvider(Principal principal) {
        // TODO Auto-generated method stub
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }

        return new ResponseEntity<>(vendorService.load(principal.getName()), HttpStatus.OK);
    }
}

