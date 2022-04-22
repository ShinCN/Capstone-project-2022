package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.singleservice.*;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.packagepost.PackagePostService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SinglePostServiceImpl implements SinglePostService {

    @Autowired
    PackagePostService packagePostService;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SinglePostRepository singlePostRepository;
    @Autowired
    PackagePostRepository packagePostRepository;
    @Autowired
    SingleCategoryRepository singleCategoryRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    GetCurrentDate getCurrentDate;
    @Autowired
    PhotoRepository photoRepository;


    @Override
    @SuppressWarnings(value = "unchecked")
    public SinglePostPagingResponse findAllSinglePost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<SinglePost> singlePosts = singlePostRepository.findAll(pageable);
        Page<SinglePost> singlePostAfterFilter = (Page<SinglePost>) singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() != null)
                .collect(Collectors.toList());

        Collection<SingleServicePostResponse> singleServicePostResponses = singlePostAfterFilter.stream()
                .map(singlePost -> SingleServicePostResponse.builder()
                        .id(singlePost.getId())
                        .serviceName(singlePost.getServiceName())
                        .price(singlePost.getPrice())
                        .photos(singlePost.getPhotos().stream().map(photo -> new PhotoResponse(photo.getId(), photo.getCaption(), photo.getUrl())).collect(Collectors.toList()))
                        .description(singlePost.getAbout())
                        .vendorAddress(singlePost.getVendorProvider().getAddress())
                        .build())
                .collect(Collectors.toList());

        return SinglePostPagingResponse.builder()
                .totalPages(singlePosts.getTotalPages())
                .pageNo(singlePosts.getNumber())
                .last(singlePosts.isLast())
                .totalElements(singlePosts.getTotalElements())
                .singleServicePostResponses(singleServicePostResponses)
                .totalElements(singlePosts.getTotalElements())
                .build();
    }

    @Override
    public void save(SingleServicePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        //check service name exist
        if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }

        SinglePost singlePost = mapToEntity(request, new SinglePost());
        singlePost.setRate(0);
        singlePost.setStatus(1);
        singlePost.setCreatedDate(getCurrentDate.now());
        singlePost.setSingleCategory(vendorProvider.getSingleCategory());
        singlePost.setVendorProvider(vendorProvider);
        singlePost.setCreatedBy(username);
        singlePost.setModifiedBy(username);

        singlePostRepository.save(singlePost);
        singlePost.getPhotos().forEach(c -> c.setSinglePost(singlePost));
        singlePostRepository.save(singlePost);
    }


    @Override
    public void update(Long id, SingleServicePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        SinglePost singlePost = getSinglePostById(id);

        //check service in current account
        if ((!singlePost.getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        if (singlePost.getServiceName().equals(request.getServiceName())
                || !singlePost.getServiceName().equals(request.getServiceName())
                && !checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            singlePost = mapToEntity(request, getSinglePostById(id));
            singlePost.setModifiedDate(getCurrentDate.now());
            singlePostRepository.save(singlePost);
        } else if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }

    }

    public SinglePost getSinglePostById(Long id) {
        return singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Single Service is not found"));
    }

    private SinglePost mapToEntity(SingleServicePostRequest singleServicePostRequest, SinglePost singlePost) {

        singlePost.setServiceName(singleServicePostRequest.getServiceName());
        singlePost.setPrice(singleServicePostRequest.getPrice());
        singlePost.setAbout(singleServicePostRequest.getDescription());
        singlePost.setModifiedDate(getCurrentDate.now());
        List<Photo> photos1 = singleServicePostRequest.getPhotos().stream().map(this::mapToEntity).collect(Collectors.toList());
        List<Photo> photos = (List<Photo>) singlePost.getPhotos();
        if (photos == null) {
            photos = new ArrayList<>(photos1);
        } else {
            photos.addAll(photos1);
        }
        singlePost.setPhotos(photos);
        singlePost.getPhotos().forEach(c -> {
            c.setSinglePost(singlePost);
        });
        return singlePost;
    }

    private Photo mapToEntity(PhotoRequest photoRequest) {

        Photo photo = new Photo();
        photo.setUrl(photoRequest.getUrl());
        photo.setCaption(photoRequest.getCaption());
        photo.setModifiedDate(getCurrentDate.now());
        photo.setCreatedDate(getCurrentDate.now());

        return photo;
    }

    List<SinglePost> findByVendorId(Long id) {
        List<SinglePost> singlePosts;
        singlePosts = singlePostRepository.findAll().stream()
                .filter(c -> c.getVendorProvider().getId().equals(id)).collect(Collectors.toList());
        return singlePosts;
    }

    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<SinglePost> singlePost = findByVendorId(id);
        if (singlePost.size() == 0) {
            return false;
        }
        singlePost.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }


    @Override
    public void delete(Long id, String username) {
        SinglePost singlePost = getSinglePostById(id);

        if ((!singlePost.getVendorProvider().getAccount().getUsername().equals(username))
        ||singlePost.getDiscardedDate() != null) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }
        singlePost.setDiscardedDate(getCurrentDate.now());
        singlePostRepository.save(singlePost);
    }

    @Override
    public Collection<SingleServicePostResponse> findAllByVendors(String username) {

        VendorProvider vendorProvider = vendorRepository.findByAccount(accountRepository.findByUsername(username));

        List<SinglePost> singlePosts = singlePostRepository.findAllByVendorProvider(vendorProvider);
        List<SinglePost> singlePostsAfterFilter =singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());

        return singlePostsAfterFilter.stream()
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
    public Collection<SingleServicePostResponse> findAllByCategoriesMyService(Long categoryId, String username) {
        Account account = accountRepository.findByUsername(username);

        List<SinglePost> singlePosts = singlePostRepository.findAllBySingleCategoryAndCustomers(singleCategoryRepository.getById(categoryId), account.getCustomer());
        List<SinglePost> singlePostsAfterFilter =singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());
        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("You have not added anything yet");
        }
        return singlePostsAfterFilter.stream()
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
    public Collection<SingleServicePostResponse> findAllByCategories(Long categoryId) {

        List<SinglePost> singlePosts = singlePostRepository.findAllBySingleCategory(singleCategoryRepository.getById(categoryId));
        List<SinglePost> singlePostsAfterFilter =singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());

        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("category does not have any one registered");
        }
        return singlePostsAfterFilter.stream()
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
}
