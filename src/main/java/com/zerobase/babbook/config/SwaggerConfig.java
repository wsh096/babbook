//package com.zerobase.babbook.config;
//
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
//
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@OpenAPIDefinition(
//    info = @Info(title = "테이블 예약서비스 API 명세서",
//        description = "테이블 마다 사람들의 예약 서비스",
//        version = "v1"))
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi chatOpenApi() {
//        String[] paths = {"/v1/**"};
//
//        return GroupedOpenApi.builder()
//            .group("테이블 예약서비스 API v1")
//            .pathsToMatch(paths)
//            .build();
//    }
//}
