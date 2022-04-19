package com.gotoubun.weddingvendor.resource.admin;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLPagingResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderPagingResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.exception.VendorNotFoundException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Admin controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    /**
     * The Account service.
     */
    @Autowired
    AccountService accountService;

    /**
     * The Admin service.
     */
    @Autowired
    AdminService adminService;

    /**
     * Get all kol collection.
     *
     * @param principal the principal
     * @param pageNo    the page no
     * @param pageSize  the page size
     * @param sortBy    the sort by
     * @param sortDir   the sort dir
     * @return the collection
     */
    @GetMapping("/kols")
    public ResponseEntity<KOLPagingResponse> getAllKol(
            Principal principal,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "1", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        return new ResponseEntity<>(adminService.findAllKOL(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * Get all kol collection.
     *
     * @param principal the principal
     * @param pageNo    the page no
     * @param pageSize  the page size
     * @param sortBy    the sort by
     * @param sortDir   the sort dir
     * @return the collection
     */
    @GetMapping("/vendors")
    public ResponseEntity<VendorProviderPagingResponse> getAllVendor(
            Principal principal,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "1", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        return new ResponseEntity<>(adminService.findAllVendor(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * Put status account response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<?> putStatusAccount(@Valid @PathVariable Long id,
                                              Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException("you don't have permission to access");
        }

        adminService.updateStatus(id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }
}
