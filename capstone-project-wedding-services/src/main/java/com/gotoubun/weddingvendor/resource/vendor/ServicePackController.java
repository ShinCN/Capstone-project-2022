package com.gotoubun.weddingvendor.resource.vendor;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccess;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.AccountService;
import com.gotoubun.weddingvendor.service.PackagePostService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/service-pack")
public class ServicePackController {
    @Autowired
    private PackagePostService packagePostService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    //add new service pack
    @PostMapping("new")
    public ResponseEntity<?> postServicePack(@Valid @RequestBody PackagePostRequest packagePostNewRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccess("you are not kol");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        PackagePost packagePost = packagePostService.save(packagePostNewRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    //update pack name
    @PostMapping("edit/{id}")
    public ResponseEntity<?> putServicePackTitle(@Valid @PathVariable Long id, @RequestBody PackagePostRequest packagePostRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccess("you are not kol");
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        PackagePost packagePost = packagePostService.update(id, packagePostRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


    //delete 1 service pack
    @DeleteMapping("remove/{id}")
    public ResponseEntity<?> deleteServicePack(@Valid @PathVariable Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException("you need to login to get access");
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccess("you are not kol");
        }
        packagePostService.delete(id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }
}
