package com.yapp.ios2.service;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    public User join(String email, String name, String password, String phone){
        User newUser = null;
        if(!duplicatedEmail(email)){
            newUser = User.builder()
                    .email(email)
                    .name(name)
                    .password(password)
                    .phone(phone)
                    .build();

            userRepository.save(newUser);
        }

        return newUser;
    }

    public User login(String email, String password){

        User user = null;

        if(duplicatedEmail(email)){
            user = userRepository.findByEmail(email);

            if(!user.getPassword().equals(password)){
                user = null;
            }
        }

        return user;
    }

    public boolean duplicatedEmail(String email){
        User user = userRepository.findByEmail(email);

        boolean result = (user != null) ? true : false ;

        return result;
    }

}
