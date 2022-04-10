package com.gotoubun.weddingvendor.service.service_pack.impl;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.ServicePackNotFound;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.service_pack.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicePackImpl implements PackagePostService {

    @Autowired
    SinglePostRepository singlePostRepository;
    @Autowired
    PackageCategoryRepository packageCategoryRepository;
    @Autowired
    KolRepository kolRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PackagePostRepository packagePostRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(PackagePostRequest request, String username) {
        Account account= accountRepository.findByUsername(username);
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(account);
        float price = 0;
        try {
            PackageCategory packageCategory = packageCategoryRepository.getById(request.getPackCategory());
//        if (checkServiceNameExisted(request.getPackTitle(), kol.getId())) {
//            throw new ServicePackAlreadyExistedException("Service Pack Name " + request.getPackTitle() + " has already existed in your account");
//        }
            PackagePost packagePost = new PackagePost();

            packagePost.setServiceName(request.getPackTitle());
            packagePost.setAbout(request.getDescription());
            packagePost.setPackageCategory(packageCategory);
            packagePost.setRate(0);
            packagePost.setKeyOpinionLeader(keyOpinionLeader);
            packagePost.setCreatedBy(account.getKeyOpinionLeader().getFullName());
            packagePost.setSinglePosts(request.getSinglePosts());
            packagePost.getSinglePosts().addAll(request.getSinglePosts().stream().map(v -> {
                SinglePost singlePost = singlePostRepository.getById(v.getId());
                singlePost.getPackagePosts().add(packagePost);
                return  singlePost;
            }).collect(Collectors.toList()));

//            packagePostRepository.save(packagePost);
            for (SinglePost c : packagePost.getSinglePosts()) {
                if(c.getPrice()!=null){
                    price += c.getPrice();
                }
            }
            packagePost.setPrice(price);
            packagePostRepository.save(packagePost);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public PackagePost update(Long id, PackagePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        PackagePost existingServicePack = getServicePostById(id).get();
        float price = 0;
        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getKeyOpinionLeader().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
            existingServicePack.setServiceName(request.getPackTitle());
            existingServicePack.setAbout(request.getDescription());
            existingServicePack.setAbout(request.getDescription());

            for ( SinglePost c : existingServicePack.getSinglePosts()) {
                price += c.getPrice();
            }
            existingServicePack.setPrice(price);

        return packagePostRepository.save(existingServicePack);
    }

    @Override
    public void delete(Long id) {
        for (SinglePost c : getServicePostById(id).get().getSinglePosts()) {
             c.getPackagePosts().remove(c);
        }
        packagePostRepository.delete(getServicePostById(id).get());
    }

    List<PackagePost> findByKolId(Long id) {
        List<PackagePost> packagePosts;
        packagePosts = packagePostRepository.findAll().stream()
                .filter(c -> c.getKeyOpinionLeader().getId().equals(id)).collect(Collectors.toList());
        return packagePosts;
    }
    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<PackagePost> pack = findByKolId(id);
        pack.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }
    public Optional<PackagePost> getServicePostById(Long id) {
        return Optional.ofNullable(packagePostRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service does not exist")));
    }
}
