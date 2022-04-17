package com.gotoubun.weddingvendor.service.vendor.impl;

import com.gotoubun.weddingvendor.data.singleservice.PhotoResponse;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostRequest;
import com.gotoubun.weddingvendor.data.singleservice.SingleServicePostResponse;
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
    public void save(SingleServicePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        SinglePost singlePost = new SinglePost();
        singlePost.setServiceName(request.getServiceName());
        singlePost.setPrice(request.getPrice());
        singlePost.setAbout(request.getDescription());
        singlePost.setPhotos(request.getPhotos());
        singlePost.setRate(0);
        singlePost.setStatus(1);
        singlePost.setSingleCategory(vendorProvider.getSingleCategory());
        singlePost.setVendorProvider(vendorProvider);
        singlePost.setCreatedBy(username);


        //check service name exist
        if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }

        singlePostRepository.save(singlePost);
        singlePost.getPhotos().forEach(c->c.setSinglePost(singlePost));
        singlePostRepository.save(singlePost);
    }

    @Override
    public void update(Long id, SingleServicePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        VendorProvider vendorProvider = vendorRepository.findByAccount(account);

        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getVendorProvider().getAccount().getUsername().equals(username))) {
            throw new SingleServicePostNotFoundException("Service not found in your account");
        }

        SinglePost singlePost = new SinglePost();
        singlePost.setServiceName(request.getServiceName());
        singlePost.setPrice(request.getPrice());
        singlePost.setAbout(request.getDescription());
        singlePost.setPhotos(request.getPhotos());
        singlePost.setRate(0);
        singlePost.setStatus(1);
        singlePost.setSingleCategory(vendorProvider.getSingleCategory());
        singlePost.setVendorProvider(vendorProvider);
        singlePost.setCreatedBy(username);

        //set photos
        singlePost.getPhotos().forEach(c->c.setSinglePost(singlePost));

        //check service name exist
        if (checkServiceNameExisted(request.getServiceName(), vendorProvider.getId())) {
            throw new SingleServicePostNotFoundException("Service Name " + request.getServiceName() + " has already existed in your account");
        }

        singlePostRepository.save(singlePost);
    }

    List<SinglePost> findByVendorId(Long id) {
        List<SinglePost> singlePosts;
        singlePosts = singlePostRepository.findAll().stream()
                .filter(c -> c.getVendorProvider().getId().equals(id)).collect(Collectors.toList());
//        if (singlePosts.size() == 0) {
//            throw new SingleServicePostNotFoundException("Vendor does not have singlePost");
//        }
        return singlePosts;
    }

    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<SinglePost> singlePost = findByVendorId(id);
        if(singlePost.size() == 0)
        {
            return false;
        }
        singlePost.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }



    @Override
    public void delete(Long id) {
        singlePostRepository.delete(getServicePostById(id).get());
    }

    @Override
    public Collection<SingleServicePostResponse> findAllByVendors(Long id) {

        VendorProvider vendorProvider = vendorRepository.getById(id);

        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();
        List<SinglePost> singlePosts= singlePostRepository.findAllByVendorProvider(vendorProvider);

       if(singlePosts.size() == 0)
       {
           throw new SingleServicePostNotFoundException("vendor does not have single post");
       }
       singlePosts.forEach(c->{
           Collection<PhotoResponse> photoResponses = new ArrayList<>();
           SingleServicePostResponse singleServicePostResponse= SingleServicePostResponse.builder()
                   .id(c.getId())
                   .serviceName(c.getServiceName())
                   .price(c.getPrice())
                   .description(c.getAbout())
                   .build();
           c.getPhotos().forEach(b->photoResponses.add(new PhotoResponse(b.getCaption(),b.getUrl())));
           singleServicePostResponse.setPhotos(photoResponses);
           singleServicePostResponses.add(singleServicePostResponse);
       });
        return singleServicePostResponses;
    }

    @Override
    public Collection<SingleServicePostResponse> findAllByCategories(Long categoryId) {

        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();
        List<SinglePost> singlePosts= singlePostRepository.findAllBySingleCategory(singleCategoryRepository.getById(categoryId));

        if(singlePosts.size() == 0)
        {
            throw new SingleServicePostNotFoundException("category does not have any one registered");
        }
        singlePosts.forEach(c->{
            Collection<PhotoResponse> photoResponses = new ArrayList<>();
            SingleServicePostResponse singleServicePostResponse= SingleServicePostResponse.builder()
                    .id(c.getId())
                    .serviceName(c.getServiceName())
                    .price(c.getPrice())
                    .description(c.getAbout())
                    .build();
            c.getPhotos().forEach(b->photoResponses.add(new PhotoResponse(b.getCaption(),b.getUrl())));
            singleServicePostResponse.setPhotos(photoResponses);
            singleServicePostResponses.add(singleServicePostResponse);
        });
        return singleServicePostResponses;
    }

    @Override
    public Collection<SingleServicePostResponse> findAll() {

        List<SinglePost> singlePosts= singlePostRepository.findAll();
        List<SingleServicePostResponse> singleServicePostResponses = new ArrayList<>();

        if(singlePosts.size() == 0)
        {
            throw new SingleServicePostNotFoundException("vendor does not have single post");
        }
        singlePosts.forEach(c->{
            Collection<PhotoResponse> photoResponses = new ArrayList<>();
            SingleServicePostResponse singleServicePostResponse= SingleServicePostResponse.builder()
                    .serviceName(c.getServiceName())
                    .price(c.getPrice())
                    .description(c.getAbout())
                    .build();
            c.getPhotos().forEach(b->photoResponses.add(new PhotoResponse(b.getCaption(),b.getUrl())));
            singleServicePostResponse.setPhotos(photoResponses);
            singleServicePostResponses.add(singleServicePostResponse);
        });
        return singleServicePostResponses;

    }

    public Optional<SinglePost> getServicePostById(Long id) {
        return Optional.ofNullable(singlePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
    }
}
