package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.data.customer.CustomerRequestOAuth;
import com.gotoubun.weddingvendor.data.customer.CustomerResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;

public interface CustomerService {
	void save(CustomerRequest customerRequest);
    void oauthSave(CustomerRequestOAuth customerRequest);
    void update(CustomerRequest kolRequest, String username);
    CustomerResponse load(String username);
	Customer findByAccount(Account account);
    void addService(Long id,  String username);
    void deleteService(Long id, String username);

}
