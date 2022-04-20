package com.gotoubun.weddingvendor.resource.planningtools;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.CustomerService;
import com.gotoubun.weddingvendor.service.customer.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Guest controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/guest")
public class GuestController {
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

    /**
     * The Guest service.
     */
    @Autowired
    GuestService guestService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Post customer response entity.
     *
     * @param guestRequest  the guest request
     * @param guestListId   the guest list id
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postGuest(@Valid @RequestBody GuestRequest guestRequest,
                                           @RequestParam Long guestListId,
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
        guestService.save(guestRequest,principal.getName(),guestListId);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllGuest(Principal principal,
                                         @RequestParam Long guestListId) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        List<GuestResponse> guestResponses = (List<GuestResponse>) guestService.findAllGuest(guestListId);
        if( guestResponses.size()==0){
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }
        return new ResponseEntity<>(guestResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putGuest(@Valid @RequestBody GuestRequest guestRequest,
                                      @RequestParam Long guestListId,
                                       @PathVariable String id,
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
        guestService.update(guestRequest,principal.getName(),guestListId,id);

        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable String id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        guestService.delete(id,principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.OK);
    }

}