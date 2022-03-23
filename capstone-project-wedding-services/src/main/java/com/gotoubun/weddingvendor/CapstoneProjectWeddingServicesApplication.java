package com.gotoubun.weddingvendor;

import com.gotoubun.weddingvendor.controller.vendor.SingleCategoryController;
import com.gotoubun.weddingvendor.entity.vendor.SingleCategory;
import com.gotoubun.weddingvendor.repository.SingleCategoryRepository;
import com.gotoubun.weddingvendor.service.IService;
import com.gotoubun.weddingvendor.service.impl.vendor.SingleCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//@EntityScan("com.gotoubun.entity")
//@EnableJpaRepositories(basePackages = {"com.gotoubun.repository"})
@EnableSwagger2
@SpringBootApplication
public class CapstoneProjectWeddingServicesApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapstoneProjectWeddingServicesApplication.class, args);
	}
}
