package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(CustomerRequest customerRequest);
    Optional<Customer> findByAccount(Optional<Account> account);
    void addService(Long id,  String username);
    void deleteService(Long id, String username);
}
