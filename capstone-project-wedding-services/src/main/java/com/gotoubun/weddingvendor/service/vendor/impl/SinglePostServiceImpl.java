package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.Price;
import com.gotoubun.weddingvendor.data.singleservice.*;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.VendorProvider;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.ServicePackNotFound;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.common.GetCurrentDate;
import com.gotoubun.weddingvendor.service.packagepost.PackagePostService;
import com.gotoubun.weddingvendor.service.vendor.SinglePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

        Page<SinglePost> singlePosts = singlePostRepository.findAllSinglePost(pageable);

        Collection<SingleServicePostResponse> singleServicePostResponses = singlePosts.stream()
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
    public Collection<SingleServicePostResponse> filterSingleService(String scope, Long categoryId, String keyword) {
        Price price = Price.of(scope);
        List<SingleServicePostResponse> filterByPrice = filterSingleServiceByPrice(price);
        List<SingleServicePostResponse> filterByCategory =  findAllByCategories(categoryId);
        List<SingleServicePostResponse> filterByKeyWord =filterSingleServiceByKeyword(keyword);

        List<SingleServicePostResponse> afterFilter= new ArrayList<>();

        filterByKeyWord.forEach(s1->{
            filterByCategory.forEach(s2->{
                filterByPrice.forEach(s3->{
                    if(s1.getId().equals(s2.getId()) && s2.getId().equals(s3.getId()))
                    {
                        afterFilter.add(s1);
                    }
                });
            });
        });

        return afterFilter;
    }

    @Override
    public List<SingleServicePostResponse> filterSingleServiceByPrice(Price price) {
        List<SinglePost> singlePosts = new ArrayList<>();
        if (price != null) {
            short filter = price.asShort();
            float value = 0;
            float from = 0;
            float to = 0;

            if (filter == 0) {
                value = 10000000;
                singlePosts = singlePostRepository.filterSingleServiceLessThan(value);
            }
            if (filter == 1) {
                from = 10000000;
                to = 20000000;
                singlePosts = singlePostRepository.filterSingleServiceBetween(from, to);
            }
            if (filter == 2) {
                from = 20000000;
                to = 30000000;
                singlePosts = singlePostRepository.filterSingleServiceBetween(from, to);
            }
            if (filter == 3) {
                from = 30000000;
                to = 40000000;
                singlePosts = singlePostRepository.filterSingleServiceBetween(from, to);
            }
            if (filter == 4) {
                from = 40000000;
                to = 50000000;
                singlePosts = singlePostRepository.filterSingleServiceBetween(from, to);
            }
            if (filter == 5) {
                value = 50000000;
                singlePosts = singlePostRepository.filterSingleServiceGreaterThan(value);
            }
            return singlePosts.stream().map(this::convertToResponse).collect(Collectors.toList());
        } else {
            return (List<SingleServicePostResponse>) findAllSinglePost();
        }

    }

    public List<SingleServicePostResponse> filterSingleServiceByKeyword(String keyword) {

        if (keyword != null) {
            return singlePostRepository.filterSingleServiceByTitleOrServiceName(keyword).stream()
                    .map(this::convertToResponse).collect(Collectors.toList());
        } else {
            return (List<SingleServicePostResponse>) findAllSinglePost();
        }

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
        singlePost.setPhotos(request.getPhotos().stream().map(this::mapToEntity)
                .collect(Collectors.toList()));

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
        
        return singlePost;
    }
    List<Photo> getPhotoBySingleService(SinglePost singlePost)
    {
        List<Photo> photos = photoRepository.findAllBySinglePost(singlePost);
        if(photos.size() != 0) return photos;
        return null;

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
                || singlePost.getDiscardedDate() != null) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }
        singlePost.setDiscardedDate(getCurrentDate.now());
        singlePostRepository.save(singlePost);
    }

    @Override
    public SingleServicePostResponse load(Long singleId) {
        return convertToResponse(findById(singleId));
    }

    SinglePost findById(Long id) {
        return singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    SingleServicePostResponse convertToResponse(SinglePost singlePost) {
        SingleServicePostResponse singleServicePostResponse = new SingleServicePostResponse();

        singleServicePostResponse.setServiceName(singlePost.getServiceName());
        singleServicePostResponse.setPrice(singlePost.getPrice());
        singleServicePostResponse.setId(singlePost.getId());
        singleServicePostResponse.setDescription(singlePost.getAbout());
        singleServicePostResponse.setRate(singlePost.getRate());
        singleServicePostResponse.setVendorAddress(singlePost.getVendorProvider().getAddress());
        singleServicePostResponse.setPhotos(singlePost.getPhotos().stream()
                .map(photo -> new PhotoResponse(photo.getId(), photo.getCaption(), photo.getUrl())).collect(Collectors.toList()));
        singleServicePostResponse.setSingleCategoryName(singlePost.getSingleCategory().getCategoryName());

        return singleServicePostResponse;
    }


    @Override
    public Collection<SingleServicePostResponse> findAllByVendors(String username) {

        VendorProvider vendorProvider = vendorRepository.findByAccount(accountRepository.findByUsername(username));

        List<SinglePost> singlePosts = singlePostRepository.findAllByVendorProvider(vendorProvider);
        List<SinglePost> singlePostsAfterFilter = singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());

        return singlePostsAfterFilter.stream().map(this::convertToResponse).collect(Collectors.toList());
    }


    @Override
    public Collection<SingleServicePostResponse> findAllSinglePost() {

        List<SinglePost> singlePosts = singlePostRepository.findAll();

        List<SinglePost> singlePostsAfterFilter = singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());

        return singlePostsAfterFilter.stream()
                .map(this::convertToResponse).collect(Collectors.toList());
    }


    @Override
    public List<SingleServicePostResponse> findAllByCategories(Long categoryId) {
        if (categoryId != null) {
            List<SinglePost> singlePosts = singlePostRepository.findAllBySingleCategory(singleCategoryRepository.getById(categoryId));
            List<SinglePost> singlePostsAfterFilter = singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                    .collect(Collectors.toList());

            if (singlePosts.size() == 0) {
                throw new SingleServicePostNotFoundException("category does not have any one registered");
            }
            return singlePostsAfterFilter.stream().map(this::convertToResponse).collect(Collectors.toList());
        }else {
            return (List<SingleServicePostResponse>) findAllSinglePost();
        }

    }

    @Override
    public Collection<SingleServicePostResponse> findAllByCustomer(String username) {
        Account account = accountRepository.findByUsername(username);

        List<SinglePost> singlePosts = singlePostRepository.findAllByCustomers(account.getCustomer());
        List<SinglePost> singlePostsAfterFilter = singlePosts.stream().filter(singlePost -> singlePost.getDiscardedDate() == null)
                .collect(Collectors.toList());
        if (singlePosts.size() == 0) {
            throw new SingleServicePostNotFoundException("You have not added anything yet");
        }
        return singlePostsAfterFilter.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}
