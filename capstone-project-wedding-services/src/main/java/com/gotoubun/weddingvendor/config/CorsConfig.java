//package com.gotoubun.weddingvendor.config;
//
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class CorsConfig {
//    public WebMvcConfigurer corsConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                WebMvcConfigurer.super.addCorsMappings(registry){
//                    registry.addMapping("/**")
//                            .allowedMethods("GET", "POST", "PUT", "DELETE")
//                            .allowedHeaders("*")
//                            .allowedOrigins("http://localhost:3000");
//                }
//            }
//        }
//    }
//}
