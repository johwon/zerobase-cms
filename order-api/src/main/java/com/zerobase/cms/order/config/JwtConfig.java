package com.zerobase.cms.order.config;

import com.zerobase.cms.domain.config.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private static final String SECRET_KEY = "ThisIsAReallyLongSecretKeyThatIsAtLeast64BytesLongForHS512Algorithm123456";

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(SECRET_KEY);
    }
}
