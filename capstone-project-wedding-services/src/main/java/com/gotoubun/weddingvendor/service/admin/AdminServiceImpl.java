package com.gotoubun.weddingvendor.service.admin;

import com.gotoubun.weddingvendor.data.admin.AccountStatusRequest;
import com.gotoubun.weddingvendor.data.kol.KOLPagingResponse;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.Admin;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.AdminRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    KolRepository kolRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public KOLPagingResponse findAllKOL(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<KeyOpinionLeader> keyOpinionLeaders = kolRepository.findAll(pageable);

        Collection<KOLResponse> kolResponses = keyOpinionLeaders.stream().map(c -> KOLResponse.builder()
                        .id(c.getId())
                        .username(c.getAccount().getUsername())
                        .status(c.getAccount().getStatus())
                        .createdDate(c.getAccount().getCreatedDate())
                        .modifiedDate(c.getAccount().getModifiedDate())
                        .fullName(c.getFullName())
                        .phone(c.getPhone())
                        .address(c.getAddress())
                        .description(c.getDescription())
                        .password(c.getAccount().getPassword())
                        .build())
                .collect(Collectors.toList());

        return KOLPagingResponse.builder()
                .totalPages(keyOpinionLeaders.getTotalPages())
                .pageNo(keyOpinionLeaders.getNumber())
                .last(keyOpinionLeaders.isLast())
                .totalElements(keyOpinionLeaders.getTotalElements())
                .kolResponses(kolResponses)
                .totalElements(keyOpinionLeaders.getTotalElements())
                .build();

    }

    public int getStatus(String username) {
        return findByUserName(username).get().getStatus();
    }

    @Override
    public void updateStatus(Long id, AccountStatusRequest accountStatusRequest) {
        Account account = accountRepository.getById(id);
        account.setStatus(accountStatusRequest.getStatus());
        accountRepository.save(account);
    }

    @Override
    public Collection<VendorProviderResponse> findAllVendor() {
        return null;
    }

    public Optional<Account> findByUserName(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username));
    }



}
