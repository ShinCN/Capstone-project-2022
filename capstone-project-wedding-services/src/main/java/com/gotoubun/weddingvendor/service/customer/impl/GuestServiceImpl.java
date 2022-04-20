package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
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
import java.util.List;

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

    @Override
    public void save(GuestRequest guestRequest, Long guestListId, String username) {
        GuestList guestList = getGuestListById(guestListId);
        //check permission
        if (!guestList.getCustomer().getAccount().getUsername().equals(username)) {
            throw new ResourceNotFoundException("you don't have permission to get access to this guest list");
        }
        Guest guest = mapToEntity(guestRequest);
        //check guest id = null
        if (guestRequest.getId() == null) {
            String id = "g" + generateRandomString(10);
            guest.setId(id);
        }
        //check guest id != null
        if (guestRequest.getId() != null) {
            guest = mapToEntity(guestRequest);
            guest.setId(guestRequest.getId());
        }
        guest.setGuestList(guestList);
        guestRepository.save(guest);
        //check guest exist
    }

    public boolean checkGuestIdExisted(String guestId, Long guestListId) {
        List<String> guestIds = new ArrayList<>();
        List<Guest> guests = guestRepository.findByGuestList(getGuestListById(guestListId));
        if (guests.size() == 0) {
            return false;
        }
        guests.forEach(guest -> guestIds.add(guest.getId()));
        return guestIds.contains(guestId);
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

    // convert request to entity
    private Guest mapToEntity(GuestRequest guestRequest) {
        Guest guest = new Guest();
        guest.setAddress(guestRequest.getAddress());
        guest.setFullName(guestRequest.getFullName());
        guest.setPhone(guestRequest.getPhone());
        guest.setMail(guestRequest.getMail());

        return guest;
    }
}
