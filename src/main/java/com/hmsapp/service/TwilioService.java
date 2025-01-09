package com.hmsapp.service;

import com.hmsapp.config.TwilioConfig;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioService(TwilioConfig twilioConfig){
        this.twilioConfig = twilioConfig;
        this.twilioConfig.init();
    }

    public void sendSms(String to, String body){

        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                body
        ).create();

        // Log success
        System.out.println("SMS sent successfully: SID = " + message.getSid());
    }

}
