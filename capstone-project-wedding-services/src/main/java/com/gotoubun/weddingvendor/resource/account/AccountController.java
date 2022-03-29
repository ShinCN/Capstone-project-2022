package com.gotoubun.weddingvendor.resource.account;

import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.payload.JWTLoginSuccessResponse;
import com.gotoubun.weddingvendor.payload.LoginRequest;
import com.gotoubun.weddingvendor.security.JwtTokenProvider;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.impl.account.AccountServiceImpl;
import com.gotoubun.weddingvendor.validator.account.AccountValidator;
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

import static com.gotoubun.weddingvendor.security.SecurityConstants.TOKEN_PREFIX;
@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Account account, BindingResult result){
        // Validate passwords match
//        accountValidator.validate(account,result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Account newUser = accountService.saveOrUpdate(account);

        return  new ResponseEntity<Account>(newUser, HttpStatus.CREATED);
    }
}
