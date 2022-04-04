//package com.gotoubun.weddingvendor.service.impl.kol;
//
//import com.gotoubun.weddingvendor.data.kol.KolRequest;
//import com.gotoubun.weddingvendor.data.kol.KolUpdateNameRequest;
//import com.gotoubun.weddingvendor.domain.user.Account;
//import com.gotoubun.weddingvendor.domain.user.KOL;
//import com.gotoubun.weddingvendor.domain.user.VendorProvider;
//import com.gotoubun.weddingvendor.domain.vendor.SingleCategory;
//import com.gotoubun.weddingvendor.repository.AccountRepository;
//import com.gotoubun.weddingvendor.repository.KolRepository;
//import com.gotoubun.weddingvendor.service.KOLService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static com.gotoubun.weddingvendor.service.common.GenerateRandomPasswordService.GenerateRandomPassword.generateRandomPassword;
//
//@Service
//public class KolServiceImpl implements KOLService {
//    @Autowired
//    public BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    KolRepository kolRepository;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Override
//    @Transactional
//    public KOL save(KolRequest kolRequest) {
//        // TODO Auto-generated method stub
//        Account account = new Account();
//        KOL kol = new KOL();
//        String password=generateRandomPassword(10);
//
//        //save account
//        account.setUsername(kolRequest.getUsername());
//        account.setPassword(bCryptPasswordEncoder.encode(password));
//        account.setRole(2);
//        accountRepository.save(account);
//        //save vendor
//        kol.setAccount(account);
//        kol.setNanoPassword(password);
//        kol.setFullName(kolRequest.getStageName());
//        kol.setMail(kolRequest.getEmail());
//        kol.setPhone(kolRequest.getPhone());
//        kol.setAddress(kolRequest.getAddress());
//        kolRepository.save(kol);
//
//        return kol;
//    }
//
//    @Override
//    public KOL update(KolUpdateNameRequest kolUpdateNameRequest) {
//        return null;
//    }
//}
