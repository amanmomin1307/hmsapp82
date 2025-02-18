package com.hmsapp.payload;

public class OTPDetails {
    private final String otp;
    private final long timestamp;

    public OTPDetails(String otp, long timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOtp() {
        return otp;
    }
}
