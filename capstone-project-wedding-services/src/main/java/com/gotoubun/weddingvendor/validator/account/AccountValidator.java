//package com.gotoubun.weddingvendor.validator.account;
//
//import com.gotoubun.weddingvendor.domain.user.Account;
//import com.gotoubun.weddingvendor.domain.user.VendorProvider;
//import com.gotoubun.weddingvendor.service.IService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import java.util.List;
//@Component
//public class AccountValidator implements Validator {
//    @Autowired
//    private IService<Account> accountIService;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        // TODO Auto-generated method stub
//        return Account.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        Account account =(Account) target;
//        List<Account> accounts =(List<Account>) accountIService.findAll();
//        for (Account account1 : accounts) {
//            if(account.getUsername().equals(account1.getUsername())) {
//                errors.rejectValue("username", "existed", "username is existed in DB");
//            }
//        }
//    }
//}
