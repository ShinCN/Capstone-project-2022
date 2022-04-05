package com.gotoubun.weddingvendor.repository;
import com.gotoubun.weddingvendor.domain.user.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByUsername(String username);
    Account getById(Long id);
    Collection<Account> findAllByRole(Integer role);
}
