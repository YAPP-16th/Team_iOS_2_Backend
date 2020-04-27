package com.yapp.ios2.controller;

import com.yapp.ios2.dto.*;
import com.yapp.ios2.service.SnsService;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.service.KakaoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    SnsService snsService;

//    KakaoService kakaoService;

    @Autowired
    JwtProvider jwtProvider;

//    @PostMapping(value = "/kakao/getInfo")
//    @ResponseBody
//    public KakaoProfileDto getInfoFromKakao(@RequestBody Map<String, String> json) {
//        System.out.println("Called getInfoFromKakao");
//        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(json.get("accesskey"));
//
//        return kakaoProfileDto;
//    }
//    @PostMapping(value = "/kakao/getToken")
//    @ResponseBody
//    public KakaoAuthDto getTokenFromKakao(@RequestBody Map<String, String> json) {
//        System.out.println("Called getTokenFromKakao");
//        KakaoAuthDto kakaoAuthDto = kakaoService.getKakaoTokenInfo(json.get("code"));
//
//        return kakaoAuthDto;
//    }

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
    public ResponseDto.JwtDto join(@RequestBody JoinDto joinInfo) {

        String jwt;
        User newUser;

        if(joinInfo.getSosial()){
            newUser = userService.join(joinInfo.getEmail(), joinInfo.getName(), joinInfo.getPhone());
        }else{
            newUser = userService.join(joinInfo.getEmail(), joinInfo.getName(), joinInfo.getPassword(), joinInfo.getPhone());
        }

        jwt = jwtProvider.createToken(newUser.getUid().toString(), newUser.getRoles());

        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);
        return jwtDto;
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
    public ResponseDto.JwtDto login(@RequestBody LoginDto loginInfo){

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

        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);

        return jwtDto;
    }

    @ApiOperation(value = "이메일 체크", notes = "" +
            "이메일이 중복되는지 확인합니다." +
            "<br>이메일이 중복된다면 true 를" +
            "<br>이메일이 중복되지 않는다면 false를" +
            "<br>리턴합니다.")
    @PostMapping("/checkEmail")
    @ResponseBody
    public BooleanResultDto duplicatedEmail(@RequestBody DuplicatedEmailDto duplicatedEmailDto){

        BooleanResultDto booleanResultDto = new BooleanResultDto();

        boolean duplicated = userService.checkEmail(duplicatedEmailDto.getEmail());

        booleanResultDto.setResult(duplicated);

        return booleanResultDto;
    }

    @ApiOperation(value = "문자 인증", notes = "" +
            "문자 인증을 진행합니다." +
            "<br>Param으로 넘긴 핸드폰 번호에 6자리의 난수가 담긴 sms를 보냅니다." +
            "<br>핸드폰 번호는 01012341234 형태로 보내주세요." +
            "<br>Response 값은 발생한 6자리의 난수입니다."
            )
    @PostMapping("/checkPhoneNum")
    @ResponseBody
    public SmsDto.SmsResponseDto sendSms(@RequestBody SmsDto.SmsRequestDto smsRequestDto){

        String num = snsService.send(smsRequestDto.getPhoneNumber());
        SmsDto.SmsResponseDto smsResponseDto = new SmsDto.SmsResponseDto();
        smsResponseDto.setNum(num);

        return smsResponseDto;

    }

    @ApiOperation(value = "이메일 변경", notes = "" +
            "이메일을 변경합니다." +
            "<br>변경할 이메일을 보내주세요." +
            "<br>JWT 토큰의 정보를 활용해 사용자의 이메일을 변경합니다."
    )
    @PostMapping("/updateEmail")
    @ResponseBody
    public ResponseDto.JwtDto updateEmail(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDto userDto){

        User user = userService.getUserByEmail(userDetails.getUsername());
        userService.updateEmail(user, userDto.getEmail());

        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);
        return jwtDto;
    }

    @ApiOperation(value = "핸드폰번호 변경", notes = "" +
            "핸드폰 번호를 변경합니다." +
            "<br>변경할 핸드폰 번호를 보내주세요." +
            "<br>JWT 토큰의 정보를 활용해 사용자의 핸드폰 번호를 변경합니다."
    )
    @PostMapping("/updatePhoneNumber")
    @ResponseBody
    public ResponseDto.JwtDto updatePhoneNumber(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDto userDto){

        User user = userService.getUserByEmail(userDetails.getUsername());
        userService.updatePhoneNumber(user, userDto.getPhoneNum());

        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);
        return jwtDto;
    }

    @ApiOperation(value = "비밀번호 변경", notes = "" +
            "비밀번호를 변경합니다." +
            "<br>변경할 비밀번호를 보내주세요." +
            "<br>JWT 토큰의 정보를 활용해 사용자의 비밀번호를 변경합니다."
    )
    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseDto.JwtDto updatePassword(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails){

        User user = userService.getUserByEmail(userDetails.getUsername());
        userService.updatePassword(user, userDto.getPassword());

        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);

        return jwtDto;
    }
}
