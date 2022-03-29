package com.gotoubun.weddingvendor.service.impl.vendor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.data.VendorProviderRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;

import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.exception.UsernameAlreadyExistsException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import org.springframework.transaction.annotation.Transactional;

import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SinglePostRepository singlePostRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public VendorProvider save(VendorProviderRequest vendor) {
        // TODO Auto-generated method stub
        Account account = new Account();
        VendorProvider vendorProvider = new VendorProvider();
        try {
            //save account
            account.setUsername(vendor.getUsername());
            account.setPassword(bCryptPasswordEncoder.encode(generateRandomPassword(10)));
            account.setRole(2);
            accountRepository.save(account);
            //save vendor
            vendorProvider.setAccount(account);
            vendorProvider.setCompany(vendor.getCompanyName());
            vendorProvider.getSingleCategory().setId(vendor.getCategoryId());
            vendorProvider.setAddress(vendor.getAddress());

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + account.getUsername() + "' already exists");
        }
        return vendorRepository.save(vendorProvider);
    }

    @Override
    public VendorProvider update(VendorProviderRequest vendorProviderRequest) {
        return null;
    }

    @Override
    public SinglePost saveSingleServicePost(SingleServicePostNewRequest request, String username) {


        SinglePost singlePost = new SinglePost();
        singlePost.setServiceName(request.getServiceName());
        singlePost.setPrice(request.getPrice());
        singlePost.setAbout(request.getDescription());
        singlePost.setPhotos(request.getPhotos());
        singlePost.setRate(0);

        VendorProvider vendorProvider = vendorRepository.findByUsername(username);
        singlePost.setVendorProvider(vendorProvider);

        List<SinglePost> singlePosts = findByVendorId(vendorProvider.getId());
        for (SinglePost s : singlePosts) {
            if (s.getServiceName().equals(singlePost.getServiceName())) {
                throw new SingleServicePostNotFoundException("Service Name " + s.getServiceName() + " has already existed in your account");
            }
        }
        return singlePostRepository.save(singlePost);

    }

    List<SinglePost> findByVendorId(long id) {
        return singlePostRepository.findAll().stream()
                .filter(c -> c.getVendorProvider().getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public SinglePost updateSingleServicePost(SingleServicePostUpdateRequest request, String username) {

        Optional<SinglePost> existingSinglePost = singlePostRepository.findById(request.getId());
        if (existingSinglePost.isPresent() && (!existingSinglePost.get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        } else if (existingSinglePost.isEmpty()) {
            throw new SingleServicePostNotFoundException("Vendor's name: '" + username + "' cannot be updated because this service doesn't exist");
        }
        VendorProvider vendorProvider = vendorRepository.findByUsername(username);
        existingSinglePost.get().setVendorProvider(vendorProvider);
        return singlePostRepository.save(existingSinglePost.get());
    }

    @Override
    public void deleteSingleServicePost(Long id) {
        singlePostRepository.delete(findById(id));
    }

    public SinglePost findById(Long id) {
        Optional<SinglePost> singlePost = singlePostRepository.findById(id);
        if (singlePost.isPresent()) {
            return singlePost.get();
        }
        throw new SingleServicePostNotFoundException("Service is not found");
    }

}
