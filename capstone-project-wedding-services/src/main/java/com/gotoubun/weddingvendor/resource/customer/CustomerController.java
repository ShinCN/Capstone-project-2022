package com.gotoubun.weddingvendor.resource.customer;

import com.gotoubun.weddingvendor.data.CustomerRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    /**
     * The Customer service.
     */
    @Autowired
    CustomerService customerService;

    /**
     * The Account service.
     */
    @Autowired
    AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Post customer response entity.
     *
     * @param customerRequest the customer request
     * @param bindingResult   the binding result
     * @param principal       the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postCustomer(@Valid @RequestBody CustomerRequest customerRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save
        customerService.save(customerRequest);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }
}
