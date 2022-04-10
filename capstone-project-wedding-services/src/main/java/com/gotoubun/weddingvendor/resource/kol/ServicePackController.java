package com.gotoubun.weddingvendor.resource.kol;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.service_pack.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/service-pack")
public class ServicePackController {
    @Autowired
    private PackagePostService packagePostService;

    @Autowired
    private IPageService<PackagePost> packagePostIPageService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<?> getAllServicePack(String keyword,
                                                                       Long packageId, Float price,
                                                                       Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccessException(NO_ACTIVATE);
        }
       List<PackagePostResponse> packagePostResponseList =packagePostService.findAll(keyword, packageId, price);
        if(packagePostResponseList.size()==0)
        {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }
        return new ResponseEntity<>(packagePostService.findAll(keyword, packageId, price), HttpStatus.OK);
    }

    @GetMapping("/search/{searchText}")
    public ResponseEntity<?> getAllServicePackByTitleOrDescription(
            Pageable pageable, @PathVariable String searchText,
            Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccessException(NO_ACTIVATE);
        }

        return new ResponseEntity<>(packagePostIPageService.findAll(pageable, searchText), HttpStatus.OK);
    }


    //add new service pack
    @PostMapping
    public ResponseEntity<?> postServicePack(@Valid @RequestBody PackagePostRequest packagePostNewRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {

            throw new AccountNotHaveAccessException("you are not kol");
        }
        int status = accountService.getStatus(principal.getName());
           if (status == 0) {
            throw new AccountNotHaveAccessException(NO_ACTIVATE);

        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        packagePostService.save(packagePostNewRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    //update pack name
    @PutMapping("{id}")
    public ResponseEntity<?> putServicePackTitle(@Valid @PathVariable Long id, @RequestBody PackagePostRequest packagePostRequest, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccessException("you are not kol");
        }
        int status = accountService.getStatus(principal.getName());
             if (status == 0) {
            throw new AccountNotHaveAccessException(NO_ACTIVATE);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        PackagePost packagePost = packagePostService.update(id, packagePostRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }


    //delete 1 service pack
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteServicePack(@Valid @PathVariable Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {

            throw new AccountNotHaveAccessException("you are not kol");
        }
    
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new AccountNotHaveAccessException(NO_ACTIVATE);

        }
        packagePostService.delete(id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }
}
