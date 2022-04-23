package com.gotoubun.weddingvendor.resource.customer;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.data.customer.CustomerResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Customer controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
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
     * @param principal the principal
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomer(Principal principal) {
        // TODO Auto-generated method stub
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        return new ResponseEntity<>(customerService.load(principal.getName()), HttpStatus.OK);
    }

    /**
     * Post customer response entity.
     *
     * @param customerRequest the customer request
     * @param bindingResult   the binding result
     * @param principal       the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postCustomer(@Valid @RequestBody CustomerRequest customerRequest,
                                          BindingResult bindingResult,
                                          Principal principal) {
        // TODO Auto-generated method stub
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save
        customerService.save(customerRequest);

        return new ResponseEntity<>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }
    /**
     * Put kol response entity.
     *
     * @param request       the request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<?> putCustomer(@Valid @RequestBody CustomerRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {
        // TODO Auto-generated method stub
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //update
        customerService.update(request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }
}
