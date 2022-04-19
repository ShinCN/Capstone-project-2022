package com.gotoubun.weddingvendor.resource.customer;


import com.gotoubun.weddingvendor.data.feedback.FeedBackRequest;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.FeedBackService;
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
@RequestMapping("/receipt/{receiptId}/feedback")
public class FeedBackController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping("{serviceId}")
    public ResponseEntity<?> postFeedBack(@Valid @PathVariable(name = "receiptId") Long receiptId,
                                         @PathVariable Long serviceId,
                                         @RequestBody FeedBackRequest request,
                                         BindingResult bindingResult,
                                         Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        feedBackService.save(receiptId, serviceId, request, principal.getName());
        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }
}
