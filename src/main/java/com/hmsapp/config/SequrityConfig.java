package com.hmsapp.config;


import com.hmsapp.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SequrityConfig {
    private JwtFilter jwtFilter;

    public SequrityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain sequrityFilterChain(
            HttpSecurity http
    ) throws Exception{

            //h(cd)2
            http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
            http.addFilterBefore(jwtFilter, AuthorizationFilter.class);   //WHITHOUT TOKEN REQUESTS I WILL NOT FILTER
            //haap
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

            return http.build();
    }
}
