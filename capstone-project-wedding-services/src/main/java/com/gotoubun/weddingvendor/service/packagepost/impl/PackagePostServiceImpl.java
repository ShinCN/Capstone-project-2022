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
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.ServicePackAlreadyExistedException;
import com.gotoubun.weddingvendor.exception.ServicePackNotFound;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.packagepost.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class PackagePostServiceImpl implements PackagePostService {

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
    GetCurrentDate getCurrentDate;

    @Override
    public void save(PackagePostRequest request, String username) {

        Account account = accountRepository.findByUsername(username);
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(account);

        PackageCategory packageCategory = getPackageCategoryById(request.getPackCategoryId());

        if (checkServiceNameExisted(request.getPackTitle(), keyOpinionLeader.getId())) {
            throw new ServicePackAlreadyExistedException("Service Pack Name " + request.getPackTitle() + " has already existed in your account");
        }

        PackagePost packagePost = new PackagePost();
        List<SinglePost> singlePosts = new ArrayList<>();

        packagePost.setServiceName(request.getPackTitle());
        packagePost.setAbout(request.getDescription());
        packagePost.setPackageCategory(packageCategory);
        packagePost.setSinglePosts(singlePosts);
        packagePost.setPrice(0.f);
        packagePost.setRate(0);
        packagePost.setKeyOpinionLeader(keyOpinionLeader);

        packagePostRepository.save(packagePost);
    }


    @Override
    public void updateSinglePost(Long id, Long singlePostId, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        SinglePost singlePost = singlePostRepository.getById(singlePostId);

        existingServicePack.setPrice(existingServicePack.getPrice() + singlePost.getPrice());
        //add single post references package post
        singlePost.getPackagePosts().add(existingServicePack);
        //add package post references single post
        existingServicePack.getSinglePosts().add(singlePost);

        singlePostRepository.save(singlePost);
    }

    @Override
    public void deleteSinglePost(Long packagePostId, Long singlePostId, String username) {

        PackagePost existingServicePack = getPackageServicePostById(packagePostId);

        if (existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }

        SinglePost singlePost = singlePostRepository.getById(singlePostId);

        existingServicePack.setPrice(existingServicePack.getPrice() - singlePost.getPrice());
        //delete single post references package post
        singlePost.getPackagePosts().remove(existingServicePack);
        //delete package post references single post
        existingServicePack.getSinglePosts().remove(singlePost);

        singlePostRepository.save(singlePost);
    }

    @Override
    public Collection<SingleServicePostResponse> findByPackagePost(Long id) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        List<SinglePost> singlePosts = singlePostRepository.findAllByPackagePosts(existingServicePack);

        return singlePosts.stream()
                .map(singlePost -> SingleServicePostResponse.builder()
                        .id(singlePost.getId())
                        .serviceName(singlePost.getServiceName())
                        .price(singlePost.getPrice())
                        .photos(singlePost.getPhotos().stream().map(photo -> new PhotoResponse(photo.getId(), photo.getCaption(), photo.getUrl())).collect(Collectors.toList()))
                        .description(singlePost.getAbout())
                        .vendorAddress(singlePost.getVendorProvider().getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PackagePost update(Long id, PackagePostRequest request, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        //check service in current account
        if ((!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        existingServicePack.setServiceName(request.getPackTitle());
        existingServicePack.setAbout(request.getDescription());

        return packagePostRepository.save(existingServicePack);
    }

    @Override
    public void delete(Long id, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        //check service in current account
        if ( !existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username) ||
                existingServicePack.getDiscardedDate() != null) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }

        existingServicePack.setDiscardedDate(getCurrentDate.now());
        packagePostRepository.save(existingServicePack);

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

    public PackagePost getPackageServicePostById(Long id) {
        return packagePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist"));
    }

    public PackageCategory getPackageCategoryById(Long id) {
        return packageCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist"));
    }

    @Override
    public List<PackagePostResponse> findAllPackagePostByFilter(String keyWord, Long packageId, Float price) {
        Optional<List<PackagePost>> packagePosts = Optional.ofNullable(packagePostRepository.filterPackagePostByServiceName(keyWord));
        List<PackagePost> packagePostsAfterFilter = new ArrayList<>();
        if (packagePosts.isPresent()) {
            packagePostsAfterFilter = packagePosts.get().stream().filter(c -> c.getPrice().equals(price)
                    && c.getPackageCategory().getId().equals(packageId)).collect(Collectors.toList());
        }
        return packagePostsAfterFilter.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<PackagePostResponse> findAllPackagePost(int pageNo, int pageSize, String sortBy, String sortDir)  {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<PackagePost> packagePosts = packagePostRepository.findAll(pageable);
        Page<PackagePost>  packagePostAfterFilter = (Page<PackagePost>) packagePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() != null)
                .collect(Collectors.toList());


        return null;
    }

    PackagePostResponse convertToResponse(PackagePost packagePost) {

        PackagePostResponse packagePostResponse = new PackagePostResponse();
        packagePostResponse.setId(packagePost.getId());
        packagePostResponse.setName(packagePost.getServiceName());
        packagePostResponse.setRate(packagePost.getRate());
        packagePostResponse.setPrice(packagePost.getPrice());
        Collection<SinglePost> singlePosts = packagePost.getSinglePosts();
        if (singlePosts.size() != 0) {
            List<Photo> photos = (List<Photo>) new ArrayList<>(singlePosts).get(0).getPhotos();
            PhotoResponse photoResponse;
            if (photos.size() == 0) {
                photoResponse = null;
            } else {
                photoResponse = new PhotoResponse(photos.get(0).getId(), photos.get(0).getCaption(), photos.get(0).getUrl());
            }
            packagePostResponse.setPhoto(photoResponse);
        }
        packagePostResponse.setKolMiniResponse(new KOLMiniResponse(packagePost.getKeyOpinionLeader().getFullName(),
                packagePost.getKeyOpinionLeader().getAvatarUrl()));
        return packagePostResponse;

    }

}
