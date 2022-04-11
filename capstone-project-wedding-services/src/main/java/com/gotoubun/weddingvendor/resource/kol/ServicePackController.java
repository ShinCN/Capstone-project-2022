package com.gotoubun.weddingvendor.resource.kol;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.servicepack.SinglePostNewRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
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

/**
 * The type Service pack controller.
 */
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

    /**
     * Gets all service pack.
     *
     * @param keyword   the keyword
     * @param packageId the package id
     * @param price     the price
     * @return the all service pack
     */
    @GetMapping
    public ResponseEntity<?> getAllServicePack(String keyword,
                                               Long packageId, Float price) {

        List<PackagePostResponse> packagePostResponseList = packagePostService.findAll(keyword, packageId, price);
        if (packagePostResponseList.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(packagePostResponseList, HttpStatus.OK);
    }

    /**
     * Post service pack response entity.
     *
     * @param packagePostRequest the package post request
     * @param bindingResult      the binding result
     * @param principal          the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postServicePack(@Valid @RequestBody PackagePostRequest packagePostRequest,
                                             BindingResult bindingResult,
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        packagePostService.save(packagePostRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put service pack response entity.
     *
     * @param packagePostRequest the package post request
     * @param id                 the id
     * @param bindingResult      the binding result
     * @param principal          the principal
     * @return the response entity
     */
    @PutMapping("{id}")
    public ResponseEntity<?> putServicePack(@Valid @RequestBody PackagePostRequest packagePostRequest,
                                            @PathVariable Long id,
                                            BindingResult bindingResult,
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
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        packagePostService.update(id, packagePostRequest, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Delete service pack response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteServicePack(@Valid @PathVariable Long id, Principal principal) {
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
        packagePostService.delete(id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Gets all single post by service pack.
     *
     * @param id the id
     * @return the all single post by service pack
     */
    @GetMapping("{id}/single-services")
    public ResponseEntity<?> getAllSinglePostByServicePack(@PathVariable Long id) {

        List<SingleServicePostResponse> singleServicePostResponses =
                (List<SingleServicePostResponse>) packagePostService.findByPackagePost(id);
        if (singleServicePostResponses.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(singleServicePostResponses, HttpStatus.OK);
    }

    /**
     * Gets all service pack by title or description.
     *
     * @param pageable   the pageable
     * @param searchText the search text
     * @param principal  the principal
     * @return the all service pack by title or description
     */
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

    /**
     * Put service pack title response entity.
     *
     * @param id           the id
     * @param singlePostId the single post id
     * @param principal    the principal
     * @return the response entity
     */
    @PostMapping("{id}/single-service")
    public ResponseEntity<?> putServicePackSinglePost(@PathVariable Long id,
                                                      Long singlePostId,
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

        packagePostService.updateSinglePost(id, singlePostId, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Delete service pack single post response entity.
     *
     * @param id           the id
     * @param singlePostId the single post id
     * @param principal    the principal
     * @return the response entity
     */
    @DeleteMapping("{id}/single-service")
    public ResponseEntity<?> deleteServicePackSinglePost(@PathVariable Long id,
                                                         Long singlePostId,
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

        packagePostService.deleteSinglePost(id, singlePostId, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

}
