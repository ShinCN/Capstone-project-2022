package com.gotoubun.weddingvendor.resource.planningtools;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.guest.GuestListResponse;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.GuestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Guest list controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/guest/list")
public class GuestListController {

    /**
     * The Guest list service.
     */
    @Autowired
    GuestListService guestListService;

    /**
     * The Account service.
     */
    @Autowired
    AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<?> getAllGuestList(Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        List<GuestListResponse> guestListResponses = (List<GuestListResponse>) guestListService.findAllGuestList(principal.getName());
        if( guestListResponses.size()==0){
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }
        return new ResponseEntity<>(guestListResponses, HttpStatus.OK);
    }
    /**
     * Post customer response entity.
     *
     * @param guestListRequest the guest list request
     * @param bindingResult    the binding result
     * @param principal        the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postGuestList(@Valid @RequestBody GuestListRequest guestListRequest,
                                           BindingResult bindingResult,
                                           Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        guestListService.save(guestListRequest, principal.getName());

        return new ResponseEntity<>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put guest list response entity.
     *
     * @param guestListRequest the guest list request
     * @param bindingResult    the binding result
     * @param principal        the principal
     * @param id               the id
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> putGuestList(@Valid @RequestBody GuestListRequest guestListRequest,
                                          BindingResult bindingResult,
                                          Principal principal,
                                          @PathVariable Long id) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        guestListService.update(guestListRequest.getName(), principal.getName(), id);

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

    /**
     * Delete guest list response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuestList(@PathVariable Long id, Principal principal) {

        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        guestListService.delete(id, principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.OK);
    }
}
