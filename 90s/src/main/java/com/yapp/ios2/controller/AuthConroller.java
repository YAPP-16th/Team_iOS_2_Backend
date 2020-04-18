package com.yapp.ios2.controller;

import com.yapp.ios2.dto.KakaoProfileDto;
import com.yapp.ios2.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthConroller {

    @Autowired
    KakaoService kakaoService;

    @PostMapping("/auth/kakao")
    @ResponseBody
    public KakaoProfileDto kakaoAuth(@RequestBody Map<String, String> json){

        String accessKey = json.get("access_key");
        System.out.println(accessKey);

        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(accessKey);

        return kakaoProfileDto;
    }
}
