package com.gotoubun.weddingvendor.resource.vendor;

import com.gotoubun.weddingvendor.data.SingleServicePostNameRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.data.VendorProviderRequest;
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

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;
import static com.gotoubun.weddingvendor.resource.MessageConstant.LOGIN_REQUIRED;

@RestController
@RequestMapping("/single-service")
public class SingleServiceController {
    @Autowired
    private SinglePostService singlePostService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private AccountService accountService;

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
        SinglePost singlePost = singlePostService.save(singleServicePost, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putSingleService(@Valid @PathVariable Long id,
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

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }


}
