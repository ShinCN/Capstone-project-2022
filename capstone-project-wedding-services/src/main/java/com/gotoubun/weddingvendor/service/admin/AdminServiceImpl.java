package com.gotoubun.weddingvendor.service.admin;

import com.gotoubun.weddingvendor.domain.user.Admin;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.AdminRepository;
import com.gotoubun.weddingvendor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;

public class AdminServiceImpl implements IService<Admin> {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AdminRepository adminRepository;
    @Override
    public Collection<Admin> findAll() {
        return null;
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Admin> findByCategoryName(String name) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Admin saveOrUpdate(Admin admin) {

        return null;
    }

    @Override
    public void deleteById(Long id) {

    }


}
