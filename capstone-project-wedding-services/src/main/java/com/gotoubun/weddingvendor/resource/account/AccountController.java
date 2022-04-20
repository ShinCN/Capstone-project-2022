package com.gotoubun.weddingvendor.resource.account;

import com.gotoubun.weddingvendor.data.account.AccountPasswordRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.payload.JWTLoginSuccessResponse;
import com.gotoubun.weddingvendor.payload.LoginRequest;
import com.gotoubun.weddingvendor.security.JwtTokenProvider;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
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

import static com.gotoubun.weddingvendor.resource.MessageConstant.LOGIN_REQUIRED;
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
        int role= accountService.getRole(loginRequest.getUsername());
        String username = loginRequest.getUsername();
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt, role, username));
    }


    /**
     * Put password response entity.
     *
     * @param passwordRequest the password request
     * @param principal       the principal
     * @return the response entity
     */
    @PutMapping("/password")
    public ResponseEntity<?> putAccountPassword(AccountPasswordRequest passwordRequest,
                                                BindingResult result,
                                                Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);

        accountService.updatePassword(passwordRequest, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Post account response entity.
     *
     * @param account the account
     * @param result  the result
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postAccount(@Valid @RequestBody Account account, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Account newUser = accountService.save(account);

        return new ResponseEntity<Account>(newUser, HttpStatus.CREATED);
    }


}