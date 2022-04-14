package com.gotoubun.weddingvendor.resource.planningtools.guestlist;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.GuestListService;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Guest list controller.
 */
@RestController
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
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        guestListService.save(guestListRequest,principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put kol response entity.
     *
     * @param guestListRequest the guest list request
     * @param id               the id
     * @param bindingResult    the binding result
     * @param principal        the principal
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> putGuestList(@Valid @RequestBody GuestListRequest guestListRequest,
                                    @PathVariable Long id,
                                    BindingResult bindingResult,
                                    Principal principal) {
        // TODO Auto-generated method stub
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        guestListService.update(guestListRequest,principal.getName(),id);
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.CREATED);
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

        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        guestListService.delete(id,principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }
}
