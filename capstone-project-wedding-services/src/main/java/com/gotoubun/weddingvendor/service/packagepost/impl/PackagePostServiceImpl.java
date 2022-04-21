package com.gotoubun.weddingvendor.service.packagepost.impl;

import com.gotoubun.weddingvendor.data.kol.KOLMiniResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.singleservice.PhotoResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KeyOpinionLeader;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.ServicePackAlreadyExistedException;
import com.gotoubun.weddingvendor.exception.ServicePackNotFound;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.packagepost.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class PackagePostServiceImpl implements PackagePostService, IPageService<PackagePost> {

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

        Account account = accountRepository.findByUsername(username);
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(account);

        if(!getPackageCategoryById(request.getPackCategoryId()).isPresent())
        {
            throw new ResourceNotFoundException("Package Post does not exist");
        }

        if (checkServiceNameExisted(request.getPackTitle(), keyOpinionLeader.getId())) {
            throw new ServicePackAlreadyExistedException("Service Pack Name " + request.getPackTitle() + " has already existed in your account");
        }

        PackagePost packagePost = new PackagePost();
        List<SinglePost> singlePosts= new ArrayList<>();

        packagePost.setServiceName(request.getPackTitle());
        packagePost.setAbout(request.getDescription());
        packagePost.setPackageCategory(packageCategoryRepository.getById(request.getPackCategoryId()));
        packagePost.setSinglePosts(singlePosts);
        packagePost.setPrice(0.f);
        packagePost.setRate(0);
        packagePost.setKeyOpinionLeader(keyOpinionLeader);

        packagePostRepository.save(packagePost);
    }


    @Override
    public void updateSinglePost(Long id,Long singlePostId, String username) {

        if (getPackageServicePostById(id).isPresent() && (!getPackageServicePostById(id).get().getKeyOpinionLeader().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        SinglePost singlePost= singlePostRepository.getById(singlePostId);
        PackagePost existingServicePack = getPackageServicePostById(id).get();

        existingServicePack.setPrice(existingServicePack.getPrice()+singlePost.getPrice());
        //add single post references package post
        singlePost.getPackagePosts().add(existingServicePack);
        //add package post references single post
        existingServicePack.getSinglePosts().add(singlePost);

        singlePostRepository.save(singlePost);
    }

    @Override
    public void deleteSinglePost(Long id,Long singlePostId, String username) {

        if (getPackageServicePostById(id).isPresent() && (!getPackageServicePostById(id).get().getKeyOpinionLeader().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }

        SinglePost singlePost= singlePostRepository.getById(singlePostId);
        PackagePost existingServicePack = getPackageServicePostById(id).get();

        existingServicePack.setPrice(existingServicePack.getPrice()-singlePost.getPrice());
        //delete single post references package post
        singlePost.getPackagePosts().remove(existingServicePack);
        //delete package post references single post
        existingServicePack.getSinglePosts().remove(singlePost);

        singlePostRepository.save(singlePost);
    }

    @Override
    public Collection<SingleServicePostResponse> findByPackagePost(Long id) {

        if (!getPackageServicePostById(id).isPresent()) {
            throw new ServicePackNotFound("This service pack is not found ");
        }

        List<SinglePost> singlePosts =singlePostRepository.findAllByPackagePosts(packagePostRepository.getById(id));

        return singlePosts.stream()
                .map(singlePost -> SingleServicePostResponse.builder()
                        .id(singlePost.getId())
                        .serviceName(singlePost.getServiceName())
                        .price(singlePost.getPrice())
                        .photos(singlePost.getPhotos().stream().map(photo -> new PhotoResponse(photo.getId(),photo.getCaption(), photo.getUrl())).collect(Collectors.toList()))
                        .description(singlePost.getAbout())
                        .vendorAddress(singlePost.getVendorProvider().getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PackagePost update(Long id, PackagePostRequest request, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id).get();

        //check service in current account
        if (getPackageServicePostById(id).isPresent() && (!getPackageServicePostById(id).get().getKeyOpinionLeader().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        existingServicePack.setServiceName(request.getPackTitle());
        existingServicePack.setAbout(request.getDescription());

        return packagePostRepository.save(existingServicePack);
    }


    @Override
    public void delete(Long id) {
          if(getPackageServicePostById(id).isPresent())
          {
              packagePostRepository.delete(getPackageServicePostById(id).get());
          }
    }

    List<PackagePost> findByKolId(Long id) {
        List<PackagePost> packagePosts;
        packagePosts = packagePostRepository.findAll().stream()
                .filter(c -> c.getKeyOpinionLeader().getId().equals(id)).collect(Collectors.toList());
        return packagePosts;
    }

    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<PackagePost> packagePosts = findByKolId(id);
        if (packagePosts.size() == 0) {
            return false;
        }
        packagePosts.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }

    public Optional<PackagePost> getPackageServicePostById(Long id) {
        return Optional.ofNullable(packagePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }

    public Optional<PackageCategory> getPackageCategoryById(Long id) {
        return Optional.ofNullable(packageCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }
    public Collection<PackagePost> findAllPackagePost() {
        return (Collection<PackagePost>) packagePostRepository.findAll();
    }


    @Override
    public Page<PackagePost> findAll(Pageable pageable, String searchText) {
        return packagePostRepository.findAllPackagePost(pageable, searchText);
    }

    @Override
    public Page<PackagePost> findAll(Pageable pageable) {
        return packagePostRepository.findAll(pageable);
    }

    @Override
    public List<PackagePostResponse> findAll(String keyWord, Long packageId, Float price) {
        List<PackagePost> packagePosts = (List<PackagePost>) packagePostRepository.filterPackagePostByServiceName(keyWord);
        List<PackagePost> packagePostsAfterFilter = packagePosts.stream().filter(c -> c.getPrice().equals(price)
                && c.getPackageCategory().getId().equals(packageId)).collect(Collectors.toList());
        List<PackagePostResponse> packagePostResponses = new ArrayList<>();
        packagePostsAfterFilter.forEach(c ->
                packagePostResponses.add(
                        new PackagePostResponse(
                                c.getId(),
                                c.getServiceName(),
                                c.getRate(),
                                new ArrayList<>(new ArrayList<>(c.getSinglePosts())
                                        .get(0).getPhotos())
                                        .get(0).getUrl(),
                                new KOLMiniResponse(c.getKeyOpinionLeader().getFullName(), c.getKeyOpinionLeader().getAvatarUrl())
                        )
                ));

        return packagePostResponses;
    }

}
