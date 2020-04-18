package com.yapp.ios2.service;

import com.yapp.ios2.config.Coolsms;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class SnsService {

    private String api_key = "NCSKL65OLKT0GSTP";
    private String api_secret = "O3VAPFSW0YNUR2GSHTTWWUC6YUFKHGBT";

    private Coolsms coolsms;

    @PostConstruct
    public void setSnsService(){
        coolsms = new Coolsms(api_key, api_secret);
    }

    public String send(String phoneNumber){

        double dValue = Math.random();

        String num = String.valueOf((int)(dValue * 1000000));

        HashMap<String, String> set = new HashMap<String, String>();
        set.put("to", phoneNumber); // 수신번호
        set.put("from", "01095233114"); // 발신번호
        set.put("text", num); // 문자내용
        set.put("type", "sms"); // 문자 타입

        JSONObject result = coolsms.send(set);

        if(!(boolean)result.get("status")){
            num = "0";
        }

        return num;

    }




}
