package com.gotoubun.weddingvendor.service.service_pack.impl;

import com.gotoubun.weddingvendor.data.kol.KOLResponse;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.data.servicepack.PackagePostResponse;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KOL;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.Photo;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.exception.ResourceNotFoundException;
import com.gotoubun.weddingvendor.exception.ServicePackAlreadyExistedException;
import com.gotoubun.weddingvendor.exception.ServicePackNotFound;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.*;
import com.gotoubun.weddingvendor.service.IPageService;
import com.gotoubun.weddingvendor.service.service_pack.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PreRemove;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicePackImpl implements PackagePostService, IPageService<PackagePost> {

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
        KOL kol = kolRepository.findByAccount(account);
        float price = 0;

        PackageCategory packageCategory = packageCategoryRepository.getById(request.getPackCategory());
//        if (checkServiceNameExisted(request.getPackTitle(), kol.getId())) {
//            throw new ServicePackAlreadyExistedException("Service Pack Name " + request.getPackTitle() + " has already existed in your account");
//        }
        PackagePost packagePost = new PackagePost();

        packagePost.setServiceName(request.getPackTitle());
        packagePost.setAbout(request.getDescription());
        packagePost.setPackageCategory(packageCategory);
        packagePost.setRate(0);
        packagePost.setKol(kol);
        packagePost.setCreatedBy(account.getKol().getFullName());
        packagePost.setSinglePosts(request.getSinglePosts());
        packagePost.getSinglePosts().addAll(request.getSinglePosts().stream().map(v -> {
            SinglePost singlePost = singlePostRepository.getById(v.getId());
            singlePost.getPackagePosts().add(packagePost);
            return singlePost;
        }).collect(Collectors.toList()));

//            packagePostRepository.save(packagePost);
        for (SinglePost c : packagePost.getSinglePosts()) {
            if (c.getPrice() != null) {
                price += c.getPrice();
            }
        }
        packagePost.setPrice(price);
        packagePostRepository.save(packagePost);

    }

    @Override
    public PackagePost update(Long id, PackagePostRequest request, String username) {
        Account account = accountRepository.findByUsername(username);
        PackagePost existingServicePack = getServicePostById(id).get();
        float price = 0;
        //check service in current account
        if (getServicePostById(id).isPresent() && (!getServicePostById(id).get().getKol().getAccount().getUsername().equals(username))) {
            throw new ServicePackNotFound("This service pack is not found in your account");
        }
        existingServicePack.setServiceName(request.getPackTitle());
        existingServicePack.setAbout(request.getDescription());
        existingServicePack.setAbout(request.getDescription());

        for (SinglePost c : existingServicePack.getSinglePosts()) {
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
        packagePosts = findAllPackagePost().stream()
                .filter(c -> c.getKol().getId().equals(id)).collect(Collectors.toList());
        return packagePosts;
    }

    boolean checkServiceNameExisted(String serviceName, Long id) {
        List<String> serviceNames = new ArrayList<>();
        List<PackagePost> pack = findByKolId(id);
        pack.forEach(c -> serviceNames.add(c.getServiceName()));
        return serviceNames.contains(serviceName);
    }

    public Optional<PackagePost> getServicePostById(Long id) {
        return Optional.ofNullable(packagePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service does not exist")));
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
                                new KOLResponse( c.getKol().getAvatarUrl(),c.getKol().getFullName())
                        )
                ));

        return packagePostResponses;
    }

}
