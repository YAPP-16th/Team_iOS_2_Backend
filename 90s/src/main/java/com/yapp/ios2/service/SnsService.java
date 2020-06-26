package com.yapp.ios2.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SnsService {
//  Twilio
    @Value("${twilio.credentials.sid}")
    private String sid;
    @Value("${twilio.credentials.token}")
    private String token;
    @Value("${twilio.fromPhoneNum}")
    private String fromPhoneNum;

    private Message message;

    @PostConstruct
    public void setSnsService(){
        Twilio.init(sid, token);
    }

    public String send(String phoneNumber){

//        인증 난수 생성
        String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        String body = String.format("Back to 90s!\n[%s]",randomNum);

        message = Message.creator(new PhoneNumber("+82" + phoneNumber), new PhoneNumber(fromPhoneNum), body).create();

        return randomNum;

    }




}
