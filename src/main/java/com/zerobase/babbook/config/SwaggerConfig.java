//package com.zerobase.babbook.config;
//
//import java.util.Collections;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.zerobase.babbook"))
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(apiInfo())
//            .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
//            .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("식당 테이블 예약 API")
//            .version("1.0")
//            .description("API 명세서")
//            .build();
//    }
//
//    @Bean
//    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//                configurer.favorPathExtension(false)
//                    .favorParameter(true)
//                    .parameterName("mediaType")
//                    .ignoreAcceptHeader(true)
//                    .useRegisteredExtensionsOnly(false)
//                    .defaultContentType(MediaType.APPLICATION_JSON)
//                    .mediaType("json", MediaType.APPLICATION_JSON)
//                    .mediaType("xml", MediaType.APPLICATION_XML);
//            }
//        };
//    }
//}
