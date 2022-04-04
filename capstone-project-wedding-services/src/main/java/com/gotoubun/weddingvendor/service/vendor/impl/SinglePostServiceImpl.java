package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.SingleServicePostNameRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostNewRequest;
import com.gotoubun.weddingvendor.data.SingleServicePostUpdateRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SinglePostServiceImpl implements SinglePostService {
    @Autowired
    IService<SingleCategory> singleCategoryIService;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SinglePostRepository singlePostRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SinglePost updateName(Long id, String serviceName, String username) {

        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);
        SinglePost existingSinglePost = getServicePostById(id).get();

        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        //check service name exist
        if (checkServiceNameExisted(serviceName, vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + serviceName + " has already existed in your account");
        }

        existingSinglePost.setServiceName(serviceName);

        return singlePostRepository.save(existingSinglePost);
    }

    @Override
    public SinglePost updatePrice(Long id, Float price, String username) {
        Account account = accountRepository.findByUsername(username);
        SinglePost existingSinglePost = getServicePostById(id).get();

        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        existingSinglePost.setPrice(price);

        return singlePostRepository.save(existingSinglePost);
    }

    @Override
    public SinglePost updateDescription(Long id, String description, String username) {
        Account account = accountRepository.findByUsername(username);
        SinglePost existingSinglePost = getServicePostById(id).get();

        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        existingSinglePost.setAbout(description);

        return singlePostRepository.save(existingSinglePost);
    }

    @Override
    public SinglePost updatePhotos(Long id, Collection<Photo> photos, String username) {
        Account account = accountRepository.findByUsername(username);
        SinglePost existingSinglePost = getServicePostById(id).get();

        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }
        photos.forEach(c->c.setSinglePost(existingSinglePost));
        existingSinglePost.setPhotos(photos);

        return singlePostRepository.save(existingSinglePost);
    }

    @Override
    public SinglePost save(SingleServicePostNewRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        SinglePost singlePost = new SinglePost();
        singlePost.setServiceName(request.getServiceName());
        singlePost.setPrice(request.getPrice());
        singlePost.setAbout(request.getDescription());
        singlePost.setPhotos(request.getPhotos());
        singlePost.setRate(0);
        singlePost.setSingleCategory(vendorProvider.getSingleCategory());
        singlePost.setVendorProvider(vendorProvider);

        //check service name exist
        if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }
        return singlePostRepository.save(singlePost);

    }

    List<SinglePost> findByVendorId(Long id) {
        List<SinglePost> singlePosts;
        singlePosts = singlePostRepository.findAll().stream()
                .filter(c -> c.getVendorProvider().getId().equals(id)).collect(Collectors.toList());
        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("Vendor does not have singlePost");
        }
        return singlePosts;
    }

    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<SinglePost> singlePost = findByVendorId(id);
        singlePost.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }

    @Override
    public void delete(Long id) {
        singlePostRepository.delete(getServicePostById(id).get());
    }

    public Optional<SinglePost> getServicePostById(Long id) {
        return Optional.ofNullable(singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }
}
