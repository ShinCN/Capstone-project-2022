package com.gotoubun.weddingvendor.service.customer.impl;

import com.gotoubun.weddingvendor.data.guest.GuestRequest;
import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Customer;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.weddingtool.Guest;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.CustomerRepository;
import com.gotoubun.weddingvendor.repository.GuestRepository;
import com.gotoubun.weddingvendor.service.customer.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;

public class GuestServiceImpl implements GuestService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    GuestRepository guestRepository;

    @Override
    public void save(GuestRequest guestRequest) {
        Customer customer = new Customer();
        Guest guest = mapToEntity(guestRequest);
        guestRepository.save(guest);

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

    @Override
    public void update(GuestRequest guestRequest) {

    }

    @Override
    public void delete(Long id) {

    }
}
