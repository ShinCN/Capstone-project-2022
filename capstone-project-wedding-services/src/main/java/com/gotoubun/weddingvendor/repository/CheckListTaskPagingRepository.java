package com.gotoubun.weddingvendor.repository;

import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.ChecklistTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CheckListTaskPagingRepository extends PagingAndSortingRepository<ChecklistTask, String> {
    Page<ChecklistTask> findByCheckList(CheckList checkList, Pageable pageable);
}
