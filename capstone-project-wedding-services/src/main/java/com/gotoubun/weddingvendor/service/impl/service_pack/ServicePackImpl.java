package com.gotoubun.weddingvendor.service.impl.service_pack;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostRequest;
import com.gotoubun.weddingvendor.domain.user.Account;
import com.gotoubun.weddingvendor.domain.user.KOL;
import com.gotoubun.weddingvendor.domain.vendor.PackageCategory;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.exception.SingleServicePostNotFoundException;
import com.gotoubun.weddingvendor.repository.AccountRepository;
import com.gotoubun.weddingvendor.repository.KolRepository;
import com.gotoubun.weddingvendor.repository.PackagePostRepository;
import com.gotoubun.weddingvendor.repository.SinglePostRepository;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.service.PackagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePackImpl implements PackagePostService {
//    @Autowired
//    IService<PackageCategory> packageCategoryIService;
    @Autowired
    KolRepository kolRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PackagePostRepository packagePostRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

//    List<PackagePost> findByKolId(Long id) {
//        List<PackagePost> packagePosts;
//        packagePosts = packageCategoryIService.findAll().stream()
//                .filter(c -> c..getId().equals(id)).collect(Collectors.toList());
//        if (packagePosts.size() == 0) {
//            throw new SingleServicePostNotFoundException("KOL does not have service pack");
//        }
//        return packagePosts;
//    }
//    boolean checkServiceNameExisted(String serviceName, Long id) {
//        List<String> serviceNames = new ArrayList<>();
//        List<PackagePost> pack = findByKolId(id);
//        pack.forEach(c -> serviceNames.add(c.getServiceName()));
//        return serviceNames.contains(serviceName);
//    }
    @Override
    public PackagePost save(PackagePostRequest request, String username) {
        Account account= accountRepository.findByUsername(username);
        KOL kol = kolRepository.findByAccount(account);

        PackagePost packagePost = new PackagePost();
        packagePost.setServiceName(request.getPackTitle());
        //check pack title exist
//        if (checkServiceNameExisted(request.getPackTitle(), kol.getId())) {
//            throw new SingleServicePostNotFoundException("Service Name " + request.getSinglePosts() + " has already existed in your account");
//        }
        packagePost.setPrice(request.getPrice());
        packagePost.setAbout(request.getDescription());
        packagePost.singlePosts(request.getSinglePosts());
        packagePost.setRate(0);
//        packagePost.set
        return packagePostRepository.save(packagePost);

    }

    @Override
    public PackagePost update(Long id, PackagePostRequest packagePostNewRequest, String username) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
