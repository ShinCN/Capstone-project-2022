package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.ChecklistTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListTaskRepository extends JpaRepository<ChecklistTask, String> {

    Page<ChecklistTask> findByCheckList(CheckList checkList, Pageable pageable);
}
