package com.gotoubun.weddingvendor.resource.account;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.KolNotFoundException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.exception.VendorNotFoundException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.payload.JWTLoginSuccessResponse;
import com.gotoubun.weddingvendor.payload.LoginRequest;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.security.JwtTokenProvider;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.account.impl.AccountServiceImpl;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.UPDATE_SUCCESS;
import static com.gotoubun.weddingvendor.security.SecurityConstants.TOKEN_PREFIX;

/**
 * The type Account controller.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AccountService accountService;
//    @Autowired
//    private AccountValidator accountValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticate user response entity.
     *
     * @param loginRequest the login request
     * @param result       the result
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    /**
     * Get all kol collection.
     *
     * @param principal the principal
     * @return the collection
     */

    @GetMapping("/kols")
    public Collection<KOLResponse> getAllKol(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException("you don't have permission to access");
        }
        Collection<KOLResponse> kolResponses = accountService.findAllKOL();
        if (kolResponses.size() == 0) {
            throw new KolNotFoundException("Kol is not found");
        }
        return accountService.findAllKOL();
    }

    /**
     * Get vendors collection.
     *
     * @param principal the principal
     * @return the collection
     */

    @GetMapping("/vendors")
    public Collection<VendorProviderResponse> getAllVendor(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException("you don't have permission to access");
        }
        Collection<VendorProviderResponse> vendorProviderResponses = accountService.findAllVendor();
        if (vendorProviderResponses.size() == 0) {
            throw new VendorNotFoundException("Kol is not found");
        }
        return vendorProviderResponses;
    }

    @GetMapping("/password")
    public ResponseEntity<MessageToUser> putPassword(AccountPasswordRequest passwordRequest, Principal principal) {
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        accountService.updatePassword(passwordRequest, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity<?> postAccount(@Valid @RequestBody Account account, BindingResult result) {
        // Validate passwords match
//        accountValidator.validate(account,result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Account newUser = accountService.save(account);

        return new ResponseEntity<Account>(newUser, HttpStatus.CREATED);
    }


    @PutMapping("/status/{id}")
    public ResponseEntity<?> putStatusAccount(@Valid @PathVariable Long id, @RequestBody AccountStatusRequest accountStatusRequest,
                                              Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccessException("you don't have permission to access");
        }

        Account account = accountService.updateStatus(id, accountStatusRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }
}