package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.data.guest.GuestResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.domain.weddingtool.GuestList;
import com.gotoubun.weddingvendor.exception.GuestIdAlreadyExistException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.repository.GuestListRepository;
import com.gotoubun.weddingvendor.repository.GuestRepository;
import com.gotoubun.weddingvendor.service.customer.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomString;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    GuestListRepository guestListRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    GuestRepository guestRepository;


    public void save(GuestRequest guestRequest, String username, Long guestListId) {
        GuestList guestList = getGuestListById(guestListId);

        if (!guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("you don't have permission to get access to this guest list");
        }
        String id = "g" + generateRandomString(10);
        Guest guest = mapToEntity(guestRequest, new Guest());
        guest.setId(id);
        guest.setGuestList(guestList);

        guestRepository.save(guest);
    }

    public void update(GuestRequest guestRequest, String username, Long guestListId, String id) {

        GuestList guestList = getGuestListById(guestListId);
        //check permission
        if (!guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("you don't have permission to get access to this guest list");
        }
        Guest guest = mapToEntity(guestRequest, getGuestById(id));

        guestRepository.save(guest);
    }

    public List<GuestList> getGuestListByCustomer(String username) {
        Account account = accountRepository.findByUsername(username);
        Customer customer = customerRepository.findByAccount(account);
        return Optional.ofNullable(guestListRepository
                        .findByCustomer(customer))
                .orElseThrow(() -> new ResourceNotFoundException("customer is not found"));
    }

    public GuestList getGuestListById(Long id) {
        return guestListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GuestList is not found"));
    }

    public Guest getGuestById(String id) {
        return guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest is not found"));
    }

    @Override
    public void delete(String id, String username) {
        Guest guest = getGuestById(id);
        if (!guest.getGuestList().getCustomer().getAccount().getUsername().equals(username)) {
            throw new GuestIdAlreadyExistException("This guest not existed in your account");
        }
        guestRepository.delete(guest);
    }

    @Override
    public Collection<GuestResponse> findAllGuest(Long guestListId) {

        List<Guest> guestResponses = guestRepository.findByGuestList(getGuestListById(guestListId));
        return guestResponses.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    // convert request to entity
    private Guest mapToEntity(GuestRequest guestRequest, Guest guest) {

        guest.setAddress(guestRequest.getAddress());
        guest.setFullName(guestRequest.getFullName());
        guest.setPhone(guestRequest.getPhone());
        guest.setMail(guestRequest.getMail());

        return guest;
    }

    // convert request to entity
    private GuestResponse convertToResponse(Guest guest) {

        GuestResponse guestResponse = new GuestResponse();
        guestResponse.setAddress(guest.getAddress());
        guestResponse.setFullName(guest.getFullName());
        guestResponse.setPhone(guest.getPhone());
        guestResponse.setMail(guest.getMail());

        return guestResponse;
    }
}
