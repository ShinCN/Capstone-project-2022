package com.gotoubun.weddingvendor.resource.planningtools;

import com.gotoubun.weddingvendor.data.checklist.CheckListTaskPagingResponse;
import com.gotoubun.weddingvendor.data.checklist.CheckListTaskRequest;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.CheckListTaskService;
import com.gotoubun.weddingvendor.service.customer.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

/**
 * The type Check list task controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/checklist/task")
public class CheckListTaskController {
    /**
     * The Customer service.
     */
    @Autowired
    CheckListTaskService checkListTaskService;

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
     * Post check list task response entity.
     *
     * @param checkListTaskRequest the check list task request
     * @param bindingResult        the binding result
     * @param principal            the principal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> postCheckListTask(@Valid @RequestBody CheckListTaskRequest checkListTaskRequest,
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
        checkListTaskService.save(checkListTaskRequest, principal.getName());

        return new ResponseEntity<>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * Post check list task response entity.
     *
     * @param principal the principal
     * @param pageNo    the page no
     * @param pageSize  the page size
     * @param sortBy    the sort by
     * @param sortDir   the sort dir
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> getAllCheckListTask(
            Principal principal,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dueDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //save
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        CheckListTaskPagingResponse checkListTaskPagingResponse = checkListTaskService
                .findAllCheckListTask(pageNo, pageSize, sortBy, sortDir, principal.getName());
        if (checkListTaskPagingResponse.getCheckListTaskResponseList().size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(checkListTaskPagingResponse, HttpStatus.OK);
    }

    /**
     * Put check list task response entity.
     *
     * @param checkListTaskRequest the check list task request
     * @param id                   the id
     * @param bindingResult        the binding result
     * @param principal            the principal
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> putCheckListTask(@Valid @RequestBody CheckListTaskRequest checkListTaskRequest,
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
        checkListTaskService.update(checkListTaskRequest, id, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

    /**
     * Put check list task response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<?> putCheckListTaskStatus(@PathVariable String id,
                                                    Principal principal) {
        // TODO Auto-generated method stub
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);

        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        checkListTaskService.updateStatus(id, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

    /**
     * Delete check list task response entity.
     *
     * @param id        the id
     * @param principal the principal
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCheckListTask(@PathVariable String id, Principal principal) {

        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }

        checkListTaskService.delete(id, principal.getName());

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.CREATED);
    }
}
