package com.gotoubun.weddingvendor.resource.kol;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.DeactivatedException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
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
 * The type Kol controller.
 */
@RestController
@RequestMapping("/kol")
public class KOLController {
    /**
     * The KOL service.
     */
    @Autowired
    KOLService kolService;

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
     * @param principal the principal
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<KOLResponse> getKOL(Principal principal) {
        // TODO Auto-generated method stub
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new DeactivatedException(NO_ACTIVATE);
        }

        return new ResponseEntity<>(kolService.load(principal.getName()), HttpStatus.OK);
    }

    /**
     * Post customer response entity.
     *
     * @param request       the kol request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postKOL(@Valid @RequestBody KOLRequest request,
                                     BindingResult bindingResult,
                                     Principal principal) {
        // TODO Auto-generated method stub
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        //save
        kolService.save(request);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Put kol response entity.
     *
     * @param request       the request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<?> putKOL(@Valid @RequestBody KOLRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {
        // TODO Auto-generated method stub
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 4) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        int status = accountService.getStatus(principal.getName());
        if (status == 0) {
            throw new DeactivatedException(NO_ACTIVATE);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //update
        kolService.update(request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

}
