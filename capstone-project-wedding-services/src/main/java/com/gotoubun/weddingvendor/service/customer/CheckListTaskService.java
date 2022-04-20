package com.gotoubun.weddingvendor.service.customer;

import com.gotoubun.weddingvendor.data.checklist.CheckListTaskPagingResponse;
import com.gotoubun.weddingvendor.data.checklist.CheckListTaskRequest;

public interface CheckListTaskService {
    void save(CheckListTaskRequest request, String username);
    void update(CheckListTaskRequest request,String id, String username);
    void delete(String id,String username);
    CheckListTaskPagingResponse findAllCheckListTask(int pageNo, int pageSize, String sortBy, String sortDir,String username);
}
