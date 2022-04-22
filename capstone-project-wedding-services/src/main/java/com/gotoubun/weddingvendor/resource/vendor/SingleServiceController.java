package com.gotoubun.weddingvendor.resource.vendor;

import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;


/**
 * The type Single service controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/single-service")
public class SingleServiceController {
    @Autowired
    private SinglePostService singlePostService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    /**
     * Customer add single service response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @PostMapping("{id}")
    public ResponseEntity<?> customerAddSingleService(@PathVariable Long id, Principal principal){
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        customerService.addService(id, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Gets all single service.
     *
     * @param principal the principal
     * @param pageNo    the page no
     * @param pageSize  the page size
     * @param sortBy    the sort by
     * @param sortDir   the sort dir
     * @return the all single service
     */
    @GetMapping
    public ResponseEntity<SinglePostPagingResponse> getAllSingleService(
            Principal principal,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        return new ResponseEntity<>(singlePostService.findAllSinglePost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * Post single service response entity.
     *
     * @param singleServicePost the single service post
     * @param bindingResult     the binding result
     * @param principal         the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postSingleService(@Valid @RequestBody SingleServicePostRequest singleServicePost, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
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

        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service
        singlePostService.save(singleServicePost, principal.getName());

        return new ResponseEntity<>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Gets all single service.
     *
     * @param principal the principal
     * @param id        the id
     * @return the all single service
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<Collection<SingleServicePostResponse>> getAllSingleServiceByCategory(Principal principal,
                                                                                              @PathVariable Long id) {
        return new ResponseEntity<>(singlePostService.findAllByCategories(id),HttpStatus.OK);
    }


    /**
     * Gets all single service.
     *
     * @param principal the principal
     * @return the all single service
     */
    @GetMapping("/vendor")
    public ResponseEntity<Collection<SingleServicePostResponse>> getAllSingleServiceByVendor(Principal principal) {
        //check login
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
        return new ResponseEntity<>(singlePostService.findAllByVendors(principal.getName()),HttpStatus.OK);
    }

    /**
     * Put single service response entity.
     *
     * @param id            the id
     * @param request       the request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> putSingleService(@Valid @PathVariable Long id,
                                              @RequestBody SingleServicePostRequest request,
                                              BindingResult bindingResult, Principal principal) {
        //check login
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //update service
        singlePostService.update(id, request, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


    /**
     * Delete single service response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingleService(@PathVariable Long id, Principal principal) {

        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        singlePostService.delete(id,principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }
    

}

