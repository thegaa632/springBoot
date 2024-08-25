package com.boot.s1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("----------------------configure----------------------");

        return http.build();
    }

//  정적 자원의 처리(단순 css나 js같은 파일에 대한 시큐리티 적용 해제)
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("----------------------web configure----------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
