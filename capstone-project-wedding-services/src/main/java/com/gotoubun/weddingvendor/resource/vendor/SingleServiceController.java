package com.gotoubun.weddingvendor.resource.vendor;

import com.gotoubun.weddingvendor.data.*;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.InvalidLoginResponse;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Single service controller.
 */
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
    public ResponseEntity<?> postSingleService(@Valid @RequestBody SingleServicePostNewRequest singleServicePost, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save service
        singlePostService.save(singleServicePost, principal.getName());


        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put single service service name response entity.
     *
     * @param id            the id
     * @param serviceName   the service name
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PutMapping("/service-name/{id}")
    public ResponseEntity<?> putSingleServiceServiceName(@Valid @PathVariable Long id,
                                              @RequestBody SingleServicePostNameRequest serviceName,
                                              BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 2) {
            throw new AccountNotHaveAccess("you don't have permission to access");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updateName(id, serviceName.getServiceName(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put single service service price response entity.
     *
     * @param id            the id
     * @param priceRequest  the price request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updatePrice(id, priceRequest.getPrice(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put single service service photos response entity.
     *
     * @param id            the id
     * @param photoRequest  the photo request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updatePhotos(id, photoRequest.getPhotos(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put single service service description response entity.
     *
     * @param id                 the id
     * @param descriptionRequest the description request
     * @param bindingResult      the binding result
     * @param principal          the principal
     * @return the response entity
     */
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        SinglePost singlePost = singlePostService.updateDescription(id, descriptionRequest.getDescription(), principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


}
