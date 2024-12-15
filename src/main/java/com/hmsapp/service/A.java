package com.hmsapp.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

//GENERATING PASSWORD
public class A {
    public static void main(String args[]){
        String encodedPwd = BCrypt.hashpw("Blog@123",BCrypt.gensalt());
        System.out.println(encodedPwd);
    }
}
