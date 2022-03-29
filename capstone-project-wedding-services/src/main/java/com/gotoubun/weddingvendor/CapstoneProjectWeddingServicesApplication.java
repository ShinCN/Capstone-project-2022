package com.gotoubun.weddingvendor;

import com.gotoubun.weddingvendor.repository.VendorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableJpaRepositories(basePackageClasses = VendorRepository.class)
public class
CapstoneProjectWeddingServicesApplication {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(CapstoneProjectWeddingServicesApplication.class, args);
    }

}
