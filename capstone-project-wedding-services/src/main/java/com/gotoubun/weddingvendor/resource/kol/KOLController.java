package com.gotoubun.weddingvendor.resource.kol;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.ADD_SUCCESS;

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
     * @param request    the kol request
     * @param bindingResult the binding result
     * @param principal     the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postKOL(@Valid @RequestBody KOLRequest request, BindingResult bindingResult, Principal principal) {
        // TODO Auto-generated method stub
        //check validate
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //save
        kolService.save(request);

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

}
