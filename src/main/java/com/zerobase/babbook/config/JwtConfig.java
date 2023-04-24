package com.zerobase.babbook.config;

import com.zerobase.babbook.token.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
*JWT(JSON Web Token) 인증 관련 Bean 생성
* 다른 클래스에서 주입받아 사용할 수 있도록 합
 */
@Configuration
public class JwtConfig {
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }
}
