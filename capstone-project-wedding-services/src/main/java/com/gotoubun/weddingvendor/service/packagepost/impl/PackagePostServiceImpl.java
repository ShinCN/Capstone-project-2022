package com.gotoubun.weddingvendor.service.packagepost.impl;

import com.gotoubun.weddingvendor.data.kol.KOLMiniResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostPagingResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.data.singleservice.PhotoResponse;
import com.gotoubun.weddingvendor.data.singleservice.SinglePostPagingResponse;
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
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PackagePost save(PackagePostRequest request, String username) {

        Account account = accountRepository.findByUsername(username);
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(account);

        if (checkServiceNameExisted(request.getPackTitle(), keyOpinionLeader.getId())) {
            throw new ServicePackAlreadyExistedException("Service Pack Name " + request.getPackTitle() + " has already existed in your account");
        }

        PackagePost packagePost = mapToEntity(request, new PackagePost());
        packagePost.setCreatedDate(getCurrentDate.now());
        packagePost.setKeyOpinionLeader(keyOpinionLeader);
        packagePost.setCreatedBy(keyOpinionLeader.getFullName());

        packagePostRepository.save(packagePost);
        return packagePost;
    }

    PackagePost mapToEntity(PackagePostRequest packagePostRequest, PackagePost packagePost) {
        List<SinglePost> singlePosts = new ArrayList<>();

        packagePost.setServiceName(packagePostRequest.getPackTitle());
        packagePost.setAbout(packagePostRequest.getDescription());
        packagePost.setPackageCategory(getPackageCategoryById(packagePostRequest.getPackCategoryId()));
        packagePost.setSinglePosts(singlePosts);
        packagePost.setPrice(0.f);
        packagePost.setRate(0);
        packagePost.setModifiedDate(getCurrentDate.now());

        return packagePost;
    }

    @Override
    @Transactional
    public void updateSinglePost(Long packagePostId, Long singlePostId, String username) {

        PackagePost existingServicePack = getPackageServicePostById(packagePostId);
        SinglePost singlePost = getSingleServicePostById(singlePostId);

        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        if (checkServicePostExistedInPackagePost(singlePost, existingServicePack)) {
            throw new ServicePackAlreadyExistedException("This single service has already existed in package service");
        }

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
        SinglePost singlePost = getSingleServicePostById(singlePostId);

        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }

        existingServicePack.setPrice(existingServicePack.getPrice() - singlePost.getPrice());
        //delete single post references package post
        singlePost.getPackagePosts().remove(existingServicePack);
        //delete package post references single post
        existingServicePack.getSinglePosts().remove(singlePost);

        singlePostRepository.save(singlePost);
    }

    boolean checkServicePostExistedInPackagePost(SinglePost singlePost, PackagePost packagePost) {
        return packagePost.getSinglePosts().contains(singlePost);
    }

    @Override
    public PackagePostResponse load(Long packageId, String username) {
        PackagePost existingServicePack = findById(packageId);

        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }

        return convertToResponse(existingServicePack);
    }

    private PackagePost findById(Long packageId) {
        return packagePostRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    @Override
    public List<SingleServicePostResponse> findAllSingleServiceByPackagePostAndSingleCategory(Long id,Long categoryId) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        List<SinglePost> singlePosts = singlePostRepository.findAllByPackagePosts(existingServicePack);

        List<SinglePost> singlePostsAfterFilter= singlePosts.stream().filter(singlePost -> singlePost.getSingleCategory().getId().equals(categoryId))
                .collect(Collectors.toList());

        return singlePostsAfterFilter.stream()
                .map(singlePost -> SingleServicePostResponse.builder()
                        .id(singlePost.getId())
                        .singleCategoryName(singlePost.getSingleCategory().getCategoryName())
                        .serviceName(singlePost.getServiceName())
                        .price(singlePost.getPrice())
                        .photos(singlePost.getPhotos().stream().map(photo -> new PhotoResponse(photo.getId(), photo.getCaption(), photo.getUrl())).collect(Collectors.toList()))
                        .description(singlePost.getAbout())
                        .vendorAddress(singlePost.getVendorProvider().getAddress())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public void update(Long id, PackagePostRequest request, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        //check service in current account
        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username)) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        existingServicePack.setServiceName(request.getPackTitle());
        existingServicePack.setAbout(request.getDescription());
        existingServicePack.setModifiedDate(getCurrentDate.now());

        packagePostRepository.save(existingServicePack);
    }

    @Override
    public void delete(Long id, String username) {

        PackagePost existingServicePack = getPackageServicePostById(id);

        //check service in current account
        if (!existingServicePack.getKeyOpinionLeader().getAccount().getUsername().equals(username) ||
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
    public SinglePost getSingleServicePostById(Long id) {
        return singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist"));
    }

    public PackageCategory getPackageCategoryById(Long id) {
        return packageCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist"));
    }

    @Override
    public List<PackagePostResponse> findAllPackagePostByFilter(String keyWord, Long packageId, Float price) {
        Optional<List<PackagePost>> packagePosts = Optional.ofNullable(packagePostRepository.filterPackagePostByServiceName(keyWord));
        List<PackagePost> packagePostsAfterFilter;
        if (packagePosts.isPresent()) {
            packagePostsAfterFilter = packagePosts.get().stream().filter(c -> c.getPrice().equals(price)
                    && c.getPackageCategory().getId().equals(packageId)).collect(Collectors.toList());
            return packagePostsAfterFilter.stream().map(this::convertToResponse).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<PackagePostResponse> findAllPackagePostByKeyOpinionLeader(String username) {
        KeyOpinionLeader keyOpinionLeader = kolRepository.findByAccount(accountRepository.findByUsername(username));
        Optional<List<PackagePost>> packagePosts = Optional.ofNullable(packagePostRepository.findAllPackagePostByKeyOpinionLeader(keyOpinionLeader));
        return packagePosts.map(posts -> posts.stream().map(this::convertToResponse).collect(Collectors.toList())).orElse(null);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PackagePostPagingResponse findAllPackagePost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<PackagePost> packagePosts = packagePostRepository.findAllPackagePost(pageable);

        Collection<PackagePostResponse> packagePostResponses = packagePosts.stream()
                .map(this::convertToResponse).collect(Collectors.toList());

        return PackagePostPagingResponse.builder()
                .totalPages(packagePosts.getTotalPages())
                .pageNo(packagePosts.getNumber())
                .last(packagePosts.isLast())
                .totalElements(packagePosts.getTotalElements())
                .packagePostResponses(packagePostResponses)
                .totalElements(packagePosts.getTotalElements())
                .build();
    }

    public List<PackagePostResponse> findAllPackagePost() {
        Optional<List<PackagePost>> packagePosts = Optional.ofNullable(packagePostRepository.findAllPackagePost());
        return packagePosts.map(posts -> posts.stream().map(this::convertToResponse).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public PackagePostResponse convertToResponse(PackagePost packagePost) {

        PackagePostResponse packagePostResponse = new PackagePostResponse();
        packagePostResponse.setId(packagePost.getId());
        packagePostResponse.setName(packagePost.getServiceName());
        packagePostResponse.setDescription(packagePost.getAbout());
        packagePostResponse.setRate(packagePost.getRate());
        packagePostResponse.setPrice(packagePost.getPrice());
        Collection<SinglePost> singlePosts = packagePost.getSinglePosts();
        PhotoResponse photoResponse;
        if (singlePosts.size() != 0) {
            List<Photo> photos = (List<Photo>) new ArrayList<>(singlePosts).get(0).getPhotos();
            if (photos.size() == 0) {
                photoResponse = null;
            } else {
                photoResponse = new PhotoResponse(photos.get(0).getId(), photos.get(0).getCaption(), photos.get(0).getUrl());
            }
            packagePostResponse.setPhoto(photoResponse);
        }
        if (singlePosts.size() == 0) {
            packagePostResponse.setPhoto(null);
        }
        packagePostResponse.setKolMiniResponse(new KOLMiniResponse(packagePost.getKeyOpinionLeader().getFullName(),
                packagePost.getKeyOpinionLeader().getAvatarUrl()));

        return packagePostResponse;
    }

}
