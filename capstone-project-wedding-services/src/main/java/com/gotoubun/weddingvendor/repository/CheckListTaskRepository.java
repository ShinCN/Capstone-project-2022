package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.ChecklistTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListTaskRepository extends JpaRepository<ChecklistTask, String> {

    List<ChecklistTask> findByCheckList(CheckList checkList);
    Page<ChecklistTask> findByCheckList(CheckList checkList, Pageable pageable);
}
