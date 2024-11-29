package com.hmsapp.controller;

import com.hmsapp.entity.User;
import com.hmsapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserRepository userRepository;

    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(
      @RequestBody User user
    ){
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity("User alerdly exist ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity("Email alerdly exist ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return new ResponseEntity("Mobile alerdly exist ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
