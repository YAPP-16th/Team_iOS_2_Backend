package com.yapp.ios2.controller;

import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.*;
import com.yapp.ios2.service.KakaoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Api(tags = {"1. User"})
@RestController
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    KakaoService kakaoService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping(value = "/kakao/getInfo")
    @ResponseBody
    public KakaoProfileDto getInfoFromKakao(@RequestBody Map<String, String> json) {
        System.out.println("Called getInfoFromKakao");
        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(json.get("accesskey"));

        return kakaoProfileDto;
    }
    @PostMapping(value = "/kakao/getToken")
    @ResponseBody
    public KakaoAuthDto getTokenFromKakao(@RequestBody Map<String, String> json) {
        System.out.println("Called getTokenFromKakao");
        KakaoAuthDto kakaoAuthDto = kakaoService.getKakaoTokenInfo(json.get("code"));

        return kakaoAuthDto;
    }

    @ApiOperation(value = "회원가입", notes = "" +
            "두가 방식으로 회원가입 할 수 있습니다." +
            "<br>공통으로 받아오는 정보는 같습니다." +
            "<br>이메일, 닉네임, 핸드폰번호 는 공통 필수 정보입니다. 또한 Sosial 여부를 보내주세요." +
            "<br>1. 카카오 (sosial = true)" +
            "<br>패스워드를 보내지 않아도 됩니다." +
            "<br>2. 일반 회원가입( sosial = false)" +
            "<br>패스워드를 보내주세요." +
            "<br>리턴값은 JWT 입니다.")
    @PostMapping(value = "/join")
    @ResponseBody
    public String join(@RequestBody JoinDto joinInfo) {
        String jwt;
        User newUser;
        if(joinInfo.getSosial()){
            newUser = userService.join(joinInfo.getEmail(), joinInfo.getName(), joinInfo.getPassword(), joinInfo.getPhone());
        }else{
            newUser = userService.join(joinInfo.getEmail(), joinInfo.getName(), joinInfo.getPhone());
        }
        jwt = jwtProvider.createToken(newUser.getUid().toString(), newUser.getRoles());
        return jwt;
    }

    @ApiOperation(value = "로그인", notes = "" +
            "두가지 방식으로 로그인할지 수 있습니다." +
            "<br>1. 카카오( sosial = true )" +
            "<br>카카오에서 받아온 email을 넘겨주세요." +
            "<br>2. 일반 로그인( sosial = false )" +
            "<br>이메일, 비밀번호를 보내주세요." +
            "<br>리턴값은 JWT 입니다.")
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody LoginDto loginInfo){

        String jwt = null;

        if(loginInfo.getSosial()){

//          Sosial Login

            Optional<User> user = Optional.of(userService.getUserByEmail(loginInfo.getEmail()));

            if(user.isPresent()){
                jwt = jwtProvider.createToken(user.get().getUid().toString(), user.get().getRoles());
            }

        }else{

//          No Sosial Login

            Optional<User> user = Optional.of(userService.login(loginInfo.getEmail(), loginInfo.getPassword()));

            if(user.isPresent()){
                jwt = jwtProvider.createToken(user.get().getUid().toString(), user.get().getRoles());
            }
        }

        return jwt;
    }

    @PostMapping("/duplicatedEmail")
    @ResponseBody
    public BooleanResultDto duplicatedEmail(@RequestBody DuplicatedEmailDto duplicatedEmailDto){

        BooleanResultDto booleanResultDto = new BooleanResultDto();

        boolean duplicated = userService.duplicatedEmail(duplicatedEmailDto.getEmail());

        booleanResultDto.setResult(duplicated);

        return booleanResultDto;
    }
}
