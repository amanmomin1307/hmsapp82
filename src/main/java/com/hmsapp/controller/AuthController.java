package com.hmsapp.controller;

//import com.hmsapp.entity.Role;
import com.hmsapp.entity.User;
import com.hmsapp.payload.JwtToken;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.OTPService;
import com.hmsapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserRepository userRepository;
    private UserService userService;
    private OTPService otpService;

    public AuthController(UserRepository userRepository, UserService userService, OTPService otpService){
        this.userRepository = userRepository;
        this.userService = userService;
        this.otpService = otpService;
    }

    //USED FOR USER SIGN UP
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

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLL_USER");
        //user.setRole("USER");
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    //USED FOR PROPERT OWNER SIGN UP
    @PostMapping("/property/sign-up")
    public ResponseEntity<?> createPropertyOwnerAccount(
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

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLL_OWNER");
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    //USED FOR CONTENT WRITER SIGN UP
    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManager( @Validated
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

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLL_BLOGMANAGER");
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);

        JwtToken jwttoken = new JwtToken();
        jwttoken.setToken(token);
        jwttoken.setType("JWT");

        if (token != null){
            return new ResponseEntity<>(jwttoken, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login-otp")
    public ResponseEntity<?> loginWithOtp(@RequestParam String mobile){
        String otp = otpService.generateOtp(mobile);


        if (otp != null){
            return new ResponseEntity<>(otp, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping ("/verify-otp")
    public boolean verifyOtp (
            @RequestParam String mobile,
            @RequestParam String otp
    ){
        return otpService.validateOTP(mobile, otp);
    }

}
