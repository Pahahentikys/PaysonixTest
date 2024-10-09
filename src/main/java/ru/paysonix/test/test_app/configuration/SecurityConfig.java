package ru.paysonix.test.test_app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Value("${test-app.header.token}")
    private String storedHeaderToken;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).disable()
                .antMatcher("/api/v1/signature/**")
                .addFilterBefore(new CustomFilter(storedHeaderToken), BasicAuthenticationFilter.class)
                .build();
    }
}
