package com.yapp.ios2.controller;

import com.yapp.ios2.dto.BooleanResultDto;
import com.yapp.ios2.dto.DuplicatedEmailDto;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping(value = "/user/join")
    @ResponseBody
    public Map<String, Object> join(@RequestBody Map<String, Optional> json) {

        Map<String, Object> result = new HashMap<>();

        String name = (String)json.get("name").get();
        String email = (String)json.get("email").get();
        String password = (String)json.get("password").get();
        String phone = (String)json.get("phone").get();

        User newUser = userService.join(email, name, password, phone);

        result.put("user", newUser);

        return result;
    }

    @PostMapping(value = "/user/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Optional> json){

        Map<String, Object> result = new HashMap<>();

        String email = (String)json.get("email").get();
        String password = (String)json.get("password").get();

        User user = userService.login(email, password);

        result.put("user", user);

        return result;
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
