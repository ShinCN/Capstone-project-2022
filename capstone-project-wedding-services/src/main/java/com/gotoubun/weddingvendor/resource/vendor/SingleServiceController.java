package com.gotoubun.weddingvendor.resource.vendor;

import com.gotoubun.weddingvendor.data.singleservice.*;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;


@RestController
@RequestMapping("/single-service")
public class SingleServiceController {
    @Autowired
    private SinglePostService singlePostService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;

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
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }

        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service
        singlePostService.save(singleServicePost, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    //Update single service
    @PutMapping("/{id}")
    public ResponseEntity<?> putSingleService(@Valid @PathVariable Long id,
                                              @RequestBody SingleServicePostRequest request,
                                              BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());

        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //update service
        singlePostService.update(id, request, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingleService(@Valid @PathVariable Long id, Principal principal) {
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }
        singlePostService.delete(id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }


    //price cap nhat tu dong
    @PutMapping("price/{id}")
    public ResponseEntity<?> putSingleServiceServicePrice(@Valid @PathVariable Long id,
                                                          @RequestBody SingleServicePostPriceRequest priceRequest,
                                                          BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updatePrice(id, priceRequest.getPrice(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("photos/{id}")
    public ResponseEntity<?> putSingleServiceServicePhotos(@Valid @PathVariable Long id,
                                                           @RequestBody SingleServicePostPhotosRequest photoRequest,
                                                           BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updatePhotos(id, photoRequest.getPhotos(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("description/{id}")
    public ResponseEntity<?> putSingleServiceServiceDescription(@Valid @PathVariable Long id,
                                                                @RequestBody SingleServicePostDescriptionRequest descriptionRequest,
                                                                BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccess("your account has not been activated yet");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updateDescription(id, descriptionRequest.getDescription(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


}

