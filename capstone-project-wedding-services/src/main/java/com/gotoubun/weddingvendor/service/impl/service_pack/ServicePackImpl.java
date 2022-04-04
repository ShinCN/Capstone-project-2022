package com.gotoubun.weddingvendor.service.impl.service_pack;

import com.gotoubun.weddingvendor.data.servicepack.PackagePostNewRequest;
import com.gotoubun.weddingvendor.domain.vendor.PackagePost;
import com.gotoubun.weddingvendor.domain.vendor.SinglePost;
import com.gotoubun.weddingvendor.service.PackagePostService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class ServicePackImpl implements PackagePostService {
    @Override
    public PackagePost save(PackagePostNewRequest packagePostNewRequest, String username) {
        return null;
    }

    @Override
    public PackagePost updateTitle(Long id, String serviceName, String username) {
        return null;
    }

    @Override
    public PackagePost updateAbout(Long id, String about, String username) {
        return null;
    }

    @Override
    public PackagePost updateServiceList(Long id, Collection<SinglePost> singlePosts, String username) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
