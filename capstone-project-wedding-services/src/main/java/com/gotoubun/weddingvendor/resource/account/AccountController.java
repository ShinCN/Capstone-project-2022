package com.gotoubun.weddingvendor.resource.account;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.payload.JWTLoginSuccessResponse;
import com.gotoubun.weddingvendor.payload.LoginRequest;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.security.JwtTokenProvider;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.impl.account.AccountServiceImpl;
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
@RestController
@RequestMapping("/admin")
public class AccountController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    AccountRepository accountRepository;
//    @Autowired
//    private AccountValidator accountValidator;

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

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody Account account, BindingResult result){
//        // Validate passwords match
////        accountValidator.validate(account,result);
//
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if(errorMap != null) return errorMap;
//
//        Account newUser = accountService.save(account);
//
//        return  new ResponseEntity<Account>(newUser, HttpStatus.CREATED);
//    }
    //get all account
    @GetMapping("/manage")
    public Collection<Account> getAllAccounts(Principal principal){
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        Collection<Account> accounts =  accountRepository.findAllByRole(2); //vendor list
        accounts.addAll(accountRepository.findAllByRole(3)); //customer list
        accounts.addAll(accountRepository.findAllByRole(4)); //kol list
        return accounts;
    }

    //get all account
    @PutMapping("/status-update")
    public ResponseEntity<?> putSingleServiceServiceName(@Valid  @RequestBody AccountStatusRequest accountStatusRequest,
                                                          Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 1) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }

        Account account = accountService.updateStatus(accountStatusRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }
}