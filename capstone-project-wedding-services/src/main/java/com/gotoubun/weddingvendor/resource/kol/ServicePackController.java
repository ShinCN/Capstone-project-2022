package com.gotoubun.weddingvendor.resource.kol;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostPagingResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.packagepost.PackagePostService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/service-pack")
public class ServicePackController {
    @Autowired
    private PackagePostService packagePostService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Gets all service pack.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @param sortBy   the sort by
     * @param sortDir  the sort dir
     * @return the all service pack
     */
    @GetMapping
    public ResponseEntity<?> getAllServicePack(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "rate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        PackagePostPagingResponse packagePostPagingResponse = packagePostService.findAllPackagePost(pageNo, pageSize, sortBy, sortDir);
        if (packagePostPagingResponse.getTotalElements() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(packagePostPagingResponse, HttpStatus.OK);
    }

    @GetMapping("/kol")
    public ResponseEntity<?> getAllServicePackByKeyOpinionLeader(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {

            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        List<PackagePostResponse> packagePostResponseList = packagePostService.findAllPackagePostByKeyOpinionLeader(principal.getName());
        if (packagePostResponseList.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(packagePostResponseList, HttpStatus.OK);
    }
//
//    @GetMapping
//    public ResponseEntity<?> getAllServicePack() {
//
//        List<PackagePostResponse> packagePostResponseList = packagePostService.findAllPackagePost();
//        if (packagePostResponseList.size() == 0) {
//            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(packagePostResponseList, HttpStatus.OK);
//    }

    /**
     * Gets all service pack.
     *
     * @param keyword   the keyword
     * @param packageId the package id
     * @param price     the price
     * @return the all service pack
     */
    @GetMapping("/filter")
    public ResponseEntity<?> getAllServicePackByFilter(String keyword,
                                                       Long packageId, Float price) {

        List<PackagePostResponse> packagePostResponseList = packagePostService.findAllPackagePostByFilter(keyword, packageId, price);
        if (packagePostResponseList.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(packagePostResponseList, HttpStatus.OK);
    }

    /**
     * Post service pack response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<PackagePostResponse> getServicePack(@PathVariable Long id, Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {

            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }

        //save service pack
        PackagePostResponse packagePostResponse = packagePostService.load(id, principal.getName());

        return ResponseEntity.ok(packagePostResponse);
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
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save service pack
        PackagePost packagePost = packagePostService.save(packagePostRequest, principal.getName());

        PackagePostResponse packagePostResponse = packagePostService.convertToResponse(packagePost);

        return new ResponseEntity<>(packagePostResponse, HttpStatus.CREATED);
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
    @PutMapping("/{id}")
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
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        packagePostService.update(id, packagePostRequest, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Delete service pack response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServicePack(@PathVariable Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {

            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        packagePostService.delete(id, principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Gets all single post by service pack.
     *
     * @param id the id
     * @return the all single post by service pack
     */
    @GetMapping("/{packagePostId}/single-services")
    public ResponseEntity<?> getAllSinglePostByServicePack(@PathVariable(name = "packagePostId") Long id,
                                                           @RequestParam(required = false) Long categoryId) {

        List<SingleServicePostResponse> singleServicePostResponses =
                packagePostService.findAllSingleServiceByPackagePostAndSingleCategory(id, categoryId);
        if (singleServicePostResponses.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(singleServicePostResponses, HttpStatus.OK);
    }


    /**
     * Put service pack title response entity.
     *
     * @param id           the id
     * @param singlePostId the single post id
     * @param principal    the principal
     * @return the response entity
     */
    @PostMapping("/{id}/single-service")
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
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }

        packagePostService.updateSinglePost(id, singlePostId, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Delete service pack single post response entity.
     *
     * @param id           the id
     * @param singlePostId the single post id
     * @param principal    the principal
     * @return the response entity
     */
    @DeleteMapping("/{id}/single-service")
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
        boolean status = accountService.getStatus(principal.getName());
        if (status == Boolean.FALSE) {
            throw new DeactivatedException(NO_ACTIVATE);
        }

        packagePostService.deleteSinglePost(id, singlePostId, principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }

}
