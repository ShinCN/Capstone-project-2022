package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.checklist.CheckListTaskPagingResponse;
import com.gotoubun.weddingvendor.data.checklist.CheckListTaskRequest;
import com.gotoubun.weddingvendor.data.checklist.CheckListTaskResponse;
import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.singleservice.PhotoResponse;
import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.weddingtool.CheckList;
import com.gotoubun.weddingvendor.domain.weddingtool.ChecklistTask;
import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import com.gotoubun.weddingvendor.exception.GuestIdAlreadyExistException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.CheckListTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomString;

@Service
public class CheckListTaskServiceImpl implements CheckListTaskService {

    @Autowired
    GetCurrentDate getCurrentDate;

    @Autowired
    CheckListRepository checkListRepository;

    @Autowired
    CheckListTaskRepository checkListTaskRepository;

    @Autowired
    CheckListTaskPagingRepository checkListTaskPagingRepository;


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void save(CheckListTaskRequest request, String username) {

        CheckList checkList = getCheckListByCustomer(username);

        if (checkList != null && !checkList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("you don't have permission to get access to this check list");
        }
        String id = "clt" + generateRandomString(10);
        ChecklistTask checklistTask = mapToEntity(request);
        checklistTask.setId(id);
        checklistTask.setCheckList(checkList);
        checkListTaskRepository.save(checklistTask);

    }

    @Override
    public void update(CheckListTaskRequest request, String id, String username) {
        CheckList checkList = getCheckListByCustomer(username);

        if (!checkList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("you don't have permission to get access to this check list");
        }
        checkListTaskRepository.save(mapToEntity(request));
    }

    @Override
    public void delete(String id, String username) {
        ChecklistTask checklistTask = getCheckListTaskById(id);
        if (!checklistTask.getCheckList().getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("This CheckList not found in your account");
        }
        checkListTaskRepository.delete(checklistTask);
    }

    public CheckListTaskPagingResponse findAllCheckListTask(int pageNo, int pageSize, String sortBy, String sortDir, String username) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ChecklistTask> checklistTasks = checkListTaskRepository
                .findByCheckList(getCheckListByCustomer(username), pageable);


        Collection<CheckListTaskResponse> checkListTaskResponses = checklistTasks.stream()
                .map(checklistTask -> CheckListTaskResponse.builder()
                        .id(checklistTask.getId())
                        .createdDate(checklistTask.getCreatedDate())
                        .dueDate(checklistTask.getDueDate())
                        .status(checklistTask.isStatus())
                        .build())
                .collect(Collectors.toList());

        return CheckListTaskPagingResponse.builder()
                .totalPages(checklistTasks.getTotalPages())
                .pageNo(checklistTasks.getNumber())
                .last(checklistTasks.isLast())
                .totalElements(checklistTasks.getTotalElements())
                .checkListTaskResponseList(checkListTaskResponses)
                .totalElements(checklistTasks.getTotalElements())
                .build();
    }


    public CheckList getCheckListByCustomer(String username) {
        return checkListRepository
                .findByCustomer(customerRepository
                        .findByAccount(accountRepository
                                .findByUsername(username)));
    }

    public ChecklistTask getCheckListTaskById(String id) {
        return checkListTaskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Check List Task is not found"));
    }


    // convert request to entity
    private ChecklistTask mapToEntity(CheckListTaskRequest checkListTaskRequest) {

        ChecklistTask checklistTask = new ChecklistTask();

        checklistTask.setTaskName(checkListTaskRequest.getName());
        checklistTask.setCreatedDate(getCurrentDate.now());
        checklistTask.setStatus(false);

        return checklistTask;
    }
}
