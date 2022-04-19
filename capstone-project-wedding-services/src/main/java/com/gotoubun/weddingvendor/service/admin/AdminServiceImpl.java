package com.gotoubun.weddingvendor.service.admin;

import com.gotoubun.weddingvendor.data.kol.KOLPagingResponse;
import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderPagingResponse;
import com.gotoubun.weddingvendor.data.vendorprovider.VendorProviderResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    KolRepository kolRepository;

    @Autowired
    VendorRepository vendorRepository;

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
                        .status(c.getAccount().isStatus())
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

    @Override
    public VendorProviderPagingResponse findAllVendor(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<VendorProvider> vendorProviders = vendorRepository.findAll(pageable);

        Collection<VendorProviderResponse> vendorProviderResponses = vendorProviders.stream().map(c -> VendorProviderResponse.builder()
                        .id(c.getId())
                        .username(c.getAccount().getUsername())
                        .status(c.getAccount().isStatus())
                        .createdDate(c.getAccount().getCreatedDate())
                        .modifiedDate(c.getAccount().getModifiedDate())
                        .fullName(c.getFullName())
                        .phone(c.getPhone())
                        .address(c.getAddress())
                        .company(c.getCompany())
                        .build())
                .collect(Collectors.toList());

        return VendorProviderPagingResponse.builder()
                .totalPages(vendorProviders.getTotalPages())
                .pageNo(vendorProviders.getNumber())
                .last(vendorProviders.isLast())
                .totalElements(vendorProviders.getTotalElements())
                .vendorProviderResponses(vendorProviderResponses)
                .totalElements(vendorProviders.getTotalElements())
                .build();
    }

    @Override
    public void updateStatus(Long id) {
        Account account = accountRepository.getById(id);
        account.setStatus(!account.isStatus());
        accountRepository.save(account);
    }



    public Account findByUserName(String username) {
        return Optional.ofNullable(accountRepository.findByUsername(username)).orElseThrow(() -> new ResourceNotFoundException("username is not found"));
    }


}
