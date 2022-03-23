package com.gotoubun.weddingvendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EntityScan("com.gotoubun.entity")
//@EnableJpaRepositories(basePackages = {"com.gotoubun.repository"})
@EnableSwagger2
@SpringBootApplication
public class CapstoneProjectWeddingServicesApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapstoneProjectWeddingServicesApplication.class, args);
	}
}
