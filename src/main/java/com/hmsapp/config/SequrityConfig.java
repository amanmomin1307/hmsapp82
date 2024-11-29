package com.hmsapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SequrityConfig {

    @Bean
    public SecurityFilterChain sequrityFilterChain(
            HttpSecurity http
    ) throws Exception{

            //h(cd)2
            http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
            //haap
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

            return http.build();
    }
}
