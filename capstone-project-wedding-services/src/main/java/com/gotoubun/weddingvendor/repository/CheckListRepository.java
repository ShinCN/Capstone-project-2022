package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.ChecklistTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, String> {
    CheckList findByCustomer(Customer customer);
    Page<ChecklistTask> findByCheckList(CheckList checkList, Pageable pageable);
}
