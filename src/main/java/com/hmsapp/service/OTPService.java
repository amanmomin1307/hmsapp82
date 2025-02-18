package com.hmsapp.service;

import com.hmsapp.payload.OTPDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private final Map<String, OTPDetails> otpStorage = new HashMap<>();
    private static final int OTP_EXPIRY_TIME =  5;

    //METHOD TO GENERATE OTP
    public String generateOtp(String mobileNumber) {
        String otp = String.format("%06d", new Random().nextInt(900000));

        OTPDetails otpDetails = new OTPDetails(otp, System.currentTimeMillis());
        otpStorage.put(mobileNumber,otpDetails);
        return otp;
    }

    //METHOD TO VALIDATE OTP
    public boolean validateOTP(String mobileNumber, String otp){
        OTPDetails otpDetails = otpStorage.get(mobileNumber);

        if(otpDetails == null){
            return false;
        }

        //CHECK IF OTP IS EXPIRED
        long currentTime = System.currentTimeMillis();
        long otpTime = otpDetails.getTimestamp();
        long timeDifference = TimeUnit.MILLISECONDS.toMinutes(currentTime - otpTime);

        if(timeDifference > OTP_EXPIRY_TIME){
            otpStorage.remove(mobileNumber);    //REMOVE MOBILE NUMBER FROM OTPSTORAGE HASHMAP RETURN FALSE
            return false;
        }

        return otpDetails.getOtp().equals(otp);
    }


}
