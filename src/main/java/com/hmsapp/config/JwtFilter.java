package com.hmsapp.config;

import com.hmsapp.entity.User;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private  UserRepository userRepository;

    public JwtFilter(JWTService jwtService,UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println(token);

        if(token!=null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7 , token.length()-1);

            String username = jwtService.getUsername(jwtToken);

            Optional<User> opUsername = userRepository.findByUsername(username);

            if (opUsername.isPresent()) {
                User user = opUsername.get();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);

                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }

}