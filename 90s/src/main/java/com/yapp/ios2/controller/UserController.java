package com.yapp.ios2.controller;

import com.yapp.ios2.dto.*;
import com.yapp.ios2.service.KakaoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    KakaoService kakaoService;

    @PostMapping(value = "/user/join")
    @ResponseBody
    public User join(@RequestBody JoinDto joinInfo) {

        User newUser = userService.join(joinInfo.getEmail(), joinInfo.getName(), joinInfo.getPassword(), joinInfo.getPhone());

        return newUser;
    }

    @PostMapping(value = "/user/auth/kakao")
    @ResponseBody
    public KakaoProfileDto join(@RequestBody Map<String, String> json) {

        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(json.get("accesskey"));

        return kakaoProfileDto;
    }

    @PostMapping(value = "/user/login")
    @ResponseBody
    public User login(@RequestBody LoginDto loginInfo){

        User user = userService.login(loginInfo.getEmail(), loginInfo.getPassword());

        return user;
    }

    @PostMapping("/user/duplicatedEmail")
    @ResponseBody
    public BooleanResultDto duplicatedEmail(@RequestBody DuplicatedEmailDto duplicatedEmailDto){

        BooleanResultDto booleanResultDto = new BooleanResultDto();

        boolean duplicated = userService.duplicatedEmail(duplicatedEmailDto.getEmail());

        booleanResultDto.setResult(duplicated);

        return booleanResultDto;
    }
}
