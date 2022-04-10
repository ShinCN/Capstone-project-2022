package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.domain.user.Customer;

public interface CustomerService {
    Customer save(CustomerRequest customerRequest);

}
