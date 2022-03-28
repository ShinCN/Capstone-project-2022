package com.gotoubun.weddingvendor.service.impl.customer;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.service.IService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements IService<Customer> {
    @Override
    public Collection<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Customer> saveAll(List<Customer> t) {
        return null;
    }
}
