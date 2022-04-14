package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.repository.GuestListRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.GuestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestListServiceImpl implements GuestListService {

    @Autowired
    GuestListRepository guestListRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void save(GuestListRequest guestListRequest, String username) {
        GuestList guestList = mapToEntity(guestListRequest, username);
        guestListRepository.save(guestList);
    }

    @Override
    public void update(GuestListRequest guestListRequest, String username, Long id) {

        GuestList guestList = getGuestListById(id);

        if (guestList != null && !guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Customer not found in your Account");
        }

        guestList = mapToEntity(guestListRequest, username);
        guestList.setId(id);
        guestListRepository.save(guestList);
    }

    @Override
    public void delete(Long id, String username) {
        GuestList guestList = getGuestListById(id);

        if (guestList != null && !guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Customer not found in your Account");
        }
        guestListRepository.delete(getGuestListById(id));
    }

    public GuestList getGuestListById(long id) {
        return guestListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GuestList is not found"));
    }

    // convert request to entity
    private GuestList mapToEntity(GuestListRequest guestListRequest, String username) {

        GuestList guestList = new GuestList();
        guestList.setGuestListName(guestListRequest.getName());
        guestList.setCreatedDate(getCurrentDate.now());
        guestList.setModifiedDate(getCurrentDate.now());
        guestList.setCustomer(customerRepository.findByAccount(accountRepository.findByUsername(username)));

        return guestList;
    }


}
