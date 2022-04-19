package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.customer.CustomerRequest;
import com.gotoubun.weddingvendor.data.customer.CustomerResponse;
import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.domain.user.Customer;

public interface CustomerService {
    void save(CustomerRequest customerRequest);
    void update(CustomerRequest kolRequest, String username);
    CustomerResponse load(String username);
}
