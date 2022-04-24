package com.gotoubun.weddingvendor.service.kol.impl;

import com.gotoubun.weddingvendor.data.kol.KOLRequest;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.exception.PhoneAlreadyExistException;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.kol.KOLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomString;


@Service
public class KOLServiceImpl implements KOLService {

    @Autowired
    KolRepository kolRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GetCurrentDate getCurrentDate;

    @Override
    public KOLResponse load(String username) {
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(accountRepository.findByUsername(username));
        return convertToResponse(keyOpinionLeader);
    }

    private KOLResponse convertToResponse(KeyOpinionLeader keyOpinionLeader) {
        return KOLResponse.builder()
                .id(keyOpinionLeader.getId())
                .username(keyOpinionLeader.getEmail())
                .phone(keyOpinionLeader.getPhone())
                .fullName(keyOpinionLeader.getFullName())
                .description(keyOpinionLeader.getDescription())
                .address(keyOpinionLeader.getAddress())
                .password(keyOpinionLeader.getAccount().getPassword())
                .createdDate(keyOpinionLeader.getAccount().getCreatedDate())
                .modifiedDate(keyOpinionLeader.getAccount().getModifiedDate())
                .build();
    }

    @Transactional
    @Override
    public void save(KOLRequest kolRequest) {
        // TODO Auto-generated method stub
        Account account = new Account();

        KeyOpinionLeader keyOpinionLeader = new KeyOpinionLeader();
        String password = generateRandomString(10);
        //check username existed
        if (checkUserNameExisted(kolRequest.getEmail())) {
            throw new UsernameAlreadyExistsException("email: " + kolRequest.getEmail() + " already exist");
        }
        //save account
        account.setUsername(kolRequest.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setRole(4);
        account.setStatus(false);
        account.setCreatedDate(getCurrentDate.now());
        account.setModifiedDate(getCurrentDate.now());
        accountRepository.save(account);

        //check phone existed
        if (checkPhoneExisted(kolRequest.getPhone())) {
            throw new UsernameAlreadyExistsException("phone: " + kolRequest.getPhone() + " already exist");
        }
        //save kol
        keyOpinionLeader.setAccount(account);
        keyOpinionLeader.setEmail(kolRequest.getEmail());
        keyOpinionLeader.setFullName(kolRequest.getFullName());
        keyOpinionLeader.setPhone(kolRequest.getPhone());
        keyOpinionLeader.setAddress(kolRequest.getAddress());
        keyOpinionLeader.setNanoPassword(password);
        keyOpinionLeader.setDescription(kolRequest.getDescription());
        kolRepository.save(keyOpinionLeader);

    }

    boolean checkPhoneExisted(String phone) {
        return kolRepository.findByPhone(phone) != null;
    }

    @Override
    public void update(KOLRequest kolRequest, String username) {

        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(accountRepository.findByUsername(username));

        //check phone existed
        if (keyOpinionLeader.getPhone().equals(kolRequest.getPhone()) ||
                !keyOpinionLeader.getPhone().equals(kolRequest.getPhone())
                        && !checkPhoneExisted(kolRequest.getPhone())) {
            kolRepository.save(mapToEntity(kolRequest, keyOpinionLeader));
        } else if (checkPhoneExisted(kolRequest.getPhone())) {
            throw new PhoneAlreadyExistException("phone: " + kolRequest.getPhone() + " already existed");
        }
    }

    public KeyOpinionLeader getKeyOpinionLeaderById(Long id) {
        return kolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("KOl is not found"));
    }

    private KeyOpinionLeader mapToEntity(KOLRequest kolRequest, KeyOpinionLeader keyOpinionLeader) {

        keyOpinionLeader.setFullName(kolRequest.getFullName());
        keyOpinionLeader.setPhone(kolRequest.getPhone());
        keyOpinionLeader.setAddress(kolRequest.getAddress());
        keyOpinionLeader.setDescription(kolRequest.getDescription());
        keyOpinionLeader.getAccount().setModifiedDate(getCurrentDate.now());

        return keyOpinionLeader;
    }

    boolean checkUserNameExisted(String username) {
        return accountRepository.findByUsername(username) != null;
    }
}
