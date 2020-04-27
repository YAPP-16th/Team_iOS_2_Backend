package com.yapp.ios2.service;

import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    일반 회원가입
    public User join(String email, String name, String password, String phone){
        User newUser = null;
        if(!checkEmail(email)){
            newUser = User.builder()
                    .email(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .phone(phone)
                    .sosial(false)
                    .build();

            userRepository.save(newUser);
        }else{
            throw new IllegalArgumentException("Existing Email");
        }
        return newUser;
    }

//    카카오 회원가입
    public User join(String email, String name, String phone){
        User newUser = null;
        if(!checkEmail(email)){
            newUser = User.builder()
                    .email(email)
                    .name(name)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .phone(phone)
                    .sosial(true)
                    .build();

            userRepository.save(newUser);
        }else{
            throw new IllegalArgumentException("Existing Email");
        }
        return newUser;
    }

    public User login(String email, String password){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return user;
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 E-MAIL"));
    }

    public boolean checkEmail(String email){
        return userRepository.findByEmail(email)
                .isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(uid))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//        return userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public User updateEmail(User user, String email){
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    public User updatePhoneNumber(User user, String phoneNumber){
        user.setPhone(phoneNumber);
        userRepository.save(user);
        return user;
    }

    public User updatePassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

}
