package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestListRequest;
import com.gotoubun.weddingvendor.data.guest.GuestListResponse;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.repository.GuestListRepository;
import com.gotoubun.weddingvendor.repository.GuestRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.customer.GuestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestListServiceImpl implements GuestListService {

    @Autowired
    GuestListRepository guestListRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void save(GuestListRequest guestListRequest, String username) {

        GuestList guestList = mapToEntity(guestListRequest, username);
        guestList.setCreatedDate(getCurrentDate.now());
        guestListRepository.save(guestList);
    }

    @Override
    public void update(String guestListName, String username, Long id) {

        GuestList guestList = getGuestListById(id);
        if (!guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Customer not found in your Account");
        }

        guestList.setGuestListName(guestListName);
        guestListRepository.save(guestList);
    }

    @Override
    public void delete(Long id, String username) {
        GuestList guestList = getGuestListById(id);

        if (!guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Customer not found in your Account");
        }
        guestListRepository.delete(guestList);
    }

    public Collection<GuestListResponse> findAllGuestList(String username) {

        Optional<List<GuestList>> guestLists =getGuestListByCustomer(username);
        return guestLists.map(lists ->
                lists.stream().map(this::convertToResponse).collect(Collectors.toList())).orElse(null);

    }

    public Optional<List<GuestList>> getGuestListByCustomer(String username) {
        Account account = accountRepository.findByUsername(username);
        Customer customer = customerRepository.findByAccount(account);
        return Optional.ofNullable(guestListRepository.findByCustomer(customer));
    }

    public Collection<GuestResponse> findAllGuestByGuestList(GuestList guestList) {

        List<Guest> guestResponses = guestRepository.findByGuestList(guestList);
        return guestResponses.stream().map(this::convertToResponse).collect(Collectors.toList());
    }


    private GuestResponse convertToResponse(Guest guest) {

        GuestResponse guestResponse = new GuestResponse();
        guestResponse.setId(guest.getId());
        guestResponse.setAddress(guest.getAddress());
        guestResponse.setFullName(guest.getFullName());
        guestResponse.setPhone(guest.getPhone());
        guestResponse.setMail(guest.getMail());

        return guestResponse;
    }

    private GuestListResponse convertToResponse(GuestList guestList) {

        GuestListResponse guestListResponse = new GuestListResponse();
        guestListResponse.setId(guestList.getId());
        guestListResponse.setName(guestList.getGuestListName());
        guestListResponse.setGuestResponses((List<GuestResponse>) findAllGuestByGuestList(guestList));

        return guestListResponse;
    }


    public GuestList getGuestListById(long id) {
        return guestListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GuestList is not found"));
    }

    // convert request to entity
    private GuestList mapToEntity(GuestListRequest guestListRequest, String username) {

        GuestList guestList = new GuestList();
        guestList.setModifiedDate(getCurrentDate.now());
        guestList.setGuestListName(guestListRequest.getName());
        guestList.setCustomer(customerRepository.findByAccount(accountRepository.findByUsername(username)));

        return guestList;
    }

}
