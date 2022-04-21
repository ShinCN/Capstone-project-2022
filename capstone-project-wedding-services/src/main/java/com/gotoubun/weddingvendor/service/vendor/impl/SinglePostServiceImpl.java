package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.singleservice.*;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.SingleCategoryRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.repository.VendorRepository;
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
    VendorRepository vendorRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SinglePostRepository singlePostRepository;
    @Autowired
    SingleCategoryRepository singleCategoryRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public SinglePostPagingResponse findAllSinglePost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<SinglePost> singlePosts = singlePostRepository.findAll(pageable);

        Collection<SingleServicePostResponse> singleServicePostResponses = singlePosts.stream()
                .map(singlePost -> SingleServicePostResponse.builder()
                        .id(singlePost.getId())
                        .serviceName(singlePost.getServiceName())
                        .price(singlePost.getPrice())
                        .photos(singlePost.getPhotos().stream().map(photo -> new PhotoResponse(photo.getCaption(), photo.getUrl())).collect(Collectors.toList()))
                        .description(singlePost.getAbout())
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

        SinglePost singlePost= mapToEntity(request, new SinglePost());
        singlePost.setSingleCategory(vendorProvider.getSingleCategory());
        singlePost.setVendorProvider(vendorProvider);
        singlePost.setCreatedBy(username);

        singlePostRepository.save(singlePost);
        singlePost.getPhotos().forEach(c -> c.setSinglePost(singlePost));
        singlePostRepository.save(singlePost);
    }


    @Override
    public void update(Long id, SingleServicePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        SinglePost singlePost = mapToEntity(request,getSinglePostById(id));

        //check service in current account
        if ((!singlePost.getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        if (singlePost.getServiceName().equals(request.getServiceName()) ) {

            singlePost.setSingleCategory(vendorProvider.getSingleCategory());
            singlePost.setVendorProvider(vendorProvider);
            singlePost.setCreatedBy(username);

        }
        else if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }

        singlePostRepository.save(singlePost);
    }

    public SinglePost getSinglePostById(Long id) {
        return singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Single Service is not found"));
    }

    private SinglePost mapToEntity(SingleServicePostRequest singleServicePostRequest, SinglePost singlePost) {

        singlePost.setServiceName(singleServicePostRequest.getServiceName());
        singlePost.setPrice(singleServicePostRequest.getPrice());
        singlePost.setAbout(singleServicePostRequest.getDescription());
        singlePost.setPhotos(singleServicePostRequest.getPhotos().stream().map(this::mapToEntity).collect(Collectors.toList()));
        singlePost.setRate(0);
        singlePost.setStatus(1);
        singlePost.getPhotos().forEach(c -> c.setSinglePost(singlePost));
        return singlePost;
    }

    private Photo mapToEntity(PhotoRequest photoRequest) {
        Photo photo = new Photo();
        photo.setCaption(photoRequest.getCaption());
        photo.setCaption(photoRequest.getCaption());
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
    public void delete(Long id) {
        singlePostRepository.delete(getSinglePostById(id));
    }

    @Override
    public Collection<SingleServicePostResponse> findAllByVendors(Long id) {

        VendorProvider vendorProvider = vendorRepository.getById(id);

        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();
        List<SinglePost> singlePosts = singlePostRepository.findAllByVendorProvider(vendorProvider);

        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("vendor does not have single post");
        }
        singlePosts.forEach(c -> {
            Collection<PhotoResponse> photoResponses = new ArrayList<>();
            SingleServicePostResponse singleServicePostResponse = SingleServicePostResponse.builder()
                    .id(c.getId())
                    .serviceName(c.getServiceName())
                    .price(c.getPrice())
                    .description(c.getAbout())
                    .vendorAddress(c.getVendorProvider().getAddress())
                    .build();
            c.getPhotos().forEach(b -> photoResponses.add(new PhotoResponse(b.getCaption(), b.getUrl())));
            singleServicePostResponse.setPhotos(photoResponses);
            singleServicePostResponses.add(singleServicePostResponse);
        });
        return singleServicePostResponses;
    }


    @Override
    public Collection<SingleServicePostResponse> findAllByCategoriesMyService(Long categoryId, String username) {
        Account account = accountRepository.findByUsername(username);
        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();
        List<SinglePost> singlePosts = singlePostRepository.findAllBySingleCategoryAndCustomers(singleCategoryRepository.getById(categoryId), account.getCustomer());

        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("You have not added anything yet");
        }
        singlePosts.forEach(c -> {
            Collection<PhotoResponse> photoResponses = new ArrayList<>();
            SingleServicePostResponse singleServicePostResponse = SingleServicePostResponse.builder()
                    .id(c.getId())
                    .serviceName(c.getServiceName())
                    .price(c.getPrice())
                    .description(c.getAbout())
                    .vendorAddress(c.getVendorProvider().getAddress())
                    .build();
            c.getPhotos().forEach(b -> photoResponses.add(new PhotoResponse(b.getCaption(), b.getUrl())));
            singleServicePostResponse.setPhotos(photoResponses);
            singleServicePostResponses.add(singleServicePostResponse);
        });
        return singleServicePostResponses;
    }

    @Override
    public Collection<SingleServicePostResponse> findAllByCategories(Long categoryId) {

        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();
        List<SinglePost> singlePosts = singlePostRepository.findAllBySingleCategory(singleCategoryRepository.getById(categoryId));

        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("category does not have any one registered");
        }
        singlePosts.forEach(c -> {
            Collection<PhotoResponse> photoResponses = new ArrayList<>();
            SingleServicePostResponse singleServicePostResponse = SingleServicePostResponse.builder()
                    .id(c.getId())
                    .serviceName(c.getServiceName())
                    .price(c.getPrice())
                    .description(c.getAbout())
                    .vendorAddress(c.getVendorProvider().getAddress())
                    .build();
            c.getPhotos().forEach(b -> photoResponses.add(new PhotoResponse(b.getCaption(), b.getUrl())));
            singleServicePostResponse.setPhotos(photoResponses);
            singleServicePostResponses.add(singleServicePostResponse);
        });
        return singleServicePostResponses;
    }

}
