package com.gotoubun.weddingvendor.resource.planningtools;

import com.gotoubun.weddingvendor.data.budget.BudgetCategoryRequest;
import com.gotoubun.weddingvendor.data.budget.BudgetResponse;
import com.gotoubun.weddingvendor.exception.AccountNotHaveAccessException;
import com.gotoubun.weddingvendor.exception.BudgetCategoryNotFound;
import com.gotoubun.weddingvendor.exception.LoginRequiredException;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.common.MapValidationErrorService;
import com.gotoubun.weddingvendor.service.customer.BudgetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static com.gotoubun.weddingvendor.resource.MessageConstant.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private BudgetCategoryService budgetCategoryService;

    @GetMapping
    public ResponseEntity<Collection<BudgetResponse>> getAllSingleService(Principal principal) {
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        Collection<BudgetResponse> responses = budgetCategoryService.findAllByBudget_Customer_Account(principal.getName());
        if (responses.size() == 0) {
            throw new BudgetCategoryNotFound("Categories are not found");
        }
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postBudgetCategory(@Valid @RequestBody BudgetCategoryRequest request, BindingResult bindingResult, Principal principal){
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        budgetCategoryService.save(request, principal.getName());

        return new ResponseEntity<MessageToUser>(new MessageToUser(ADD_SUCCESS), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putSingleService(@Valid @PathVariable Long id,
                                              @RequestBody BudgetCategoryRequest request,
                                              BindingResult bindingResult, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        //check valid attributes
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        //update service
        budgetCategoryService.update(id, request, principal.getName());

        return new ResponseEntity<>(new MessageToUser(UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSingleService(@PathVariable Long id, Principal principal) {
        //check login
        if (principal == null)
            throw new LoginRequiredException(LOGIN_REQUIRED);
        //check role
        int role = accountService.getRole(principal.getName());
        if (role != 3) {
            throw new AccountNotHaveAccessException(NO_PERMISSION);
        }
        budgetCategoryService.delete(id);

        return new ResponseEntity<>(new MessageToUser(DELETE_SUCCESS), HttpStatus.OK);
    }
}
