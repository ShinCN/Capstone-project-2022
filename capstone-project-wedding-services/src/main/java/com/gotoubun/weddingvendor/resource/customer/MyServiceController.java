package com.gotoubun.weddingvendor.resource.customer;


import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/my-service")
public class MyServiceController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SinglePostService singlePostService;

    @DeleteMapping("{serviceId}")
    public ResponseEntity<?> deleteSingleService(@PathVariable Long serviceId, Principal principal){
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        customerService.deleteService(serviceId, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

    //Get all service in my service of customer by category
    @GetMapping
    public ResponseEntity<Collection<SingleServicePostResponse>> getAllSingleServiceByCategoryCustomer(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        return new ResponseEntity<>(singlePostService.findAllByCustomer(principal.getName()),HttpStatus.OK);
    }
}
