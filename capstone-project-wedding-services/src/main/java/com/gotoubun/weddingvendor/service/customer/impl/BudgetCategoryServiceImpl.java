package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.budget.BudgetCategoryRequest;
import com.gotoubun.weddingvendor.data.budget.BudgetResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.weddingtool.BudgetCategory;
import com.gotoubun.weddingvendor.exception.BudgetCategoryExisted;
import com.gotoubun.weddingvendor.exception.BudgetCategoryNotFound;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.BudgetCategoryRepository;
import com.gotoubun.weddingvendor.service.customer.BudgetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService {

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void save(BudgetCategoryRequest budgetCategoryRequest, String username) {
        Account account = accountRepository.findByUsername(username);

        if (checkBudgetCategoryNameExisted(budgetCategoryRequest.getCategoryName())) {
            throw new BudgetCategoryExisted("This category name: " + budgetCategoryRequest.getCategoryName() + " already existed in your list");
        }

        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setCategoryName(budgetCategoryRequest.getCategoryName());
        budgetCategory.setCost(budgetCategoryRequest.getCost());
        budgetCategory.setBudget(account.getCustomer().getBudget());
        budgetCategoryRepository.save(budgetCategory);

    }

    @Override
    public BudgetCategory update(Long id, BudgetCategoryRequest request, String username) {
        BudgetCategory existingBudgetCategory = getBudgetCategoryById(id).get();

        //check service in current account
        if (getBudgetCategoryById(id).isPresent() && (!getBudgetCategoryById(id).get().getBudget().getCustomer().getAccount().getUsername().equals(username))) {
            throw new BudgetCategoryNotFound("This category is not found");
        }
        existingBudgetCategory.setCategoryName(request.getCategoryName());
        existingBudgetCategory.setCost(request.getCost());

        return budgetCategoryRepository.save(existingBudgetCategory);
    }

    @Override
    public Collection<BudgetResponse> findAllByBudget_Customer_Account(String username) {
        List<BudgetCategory> budgetCategories = budgetCategoryRepository.findAllByBudget_Customer_Account(username);
        List<BudgetResponse> budgetResponses = new ArrayList<>();
        budgetCategories.forEach(c -> {
            BudgetResponse budgetResponse = BudgetResponse.builder()
                            .id(c.getId())
                            .categoryName(c.getCategoryName())
                            .cost(c.getCost())
                            .build();
                    budgetResponses.add(budgetResponse);
                }
        );
        return budgetResponses;
    }

    @Override
    public void delete(Long id) {
        budgetCategoryRepository.delete(getBudgetCategoryById(id).get());
    }

    boolean checkUserNameExisted(String username){
        return accountRepository.findByUsername(username) != null;
    }
    boolean checkBudgetCategoryNameExisted(String categoryName){
        return budgetCategoryRepository.findByCategoryName(categoryName) != null;
    }

    public Optional<BudgetCategory> getBudgetCategoryById(Long id) {
        return Optional.ofNullable(budgetCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }

}
