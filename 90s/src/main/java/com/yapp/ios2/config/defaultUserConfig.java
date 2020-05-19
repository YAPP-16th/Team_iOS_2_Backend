package com.yapp.ios2.config;

import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@RequiredArgsConstructor

@Configuration
public class defaultUserConfig implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("CREATE DEFAULT USER");
        User defaultUser = userRepository.findByEmail("tryer@90s.com").orElse(
                User.builder()
                        .email("tryer@90s.com")
                        .name("90s_tryer")
                        .roles(Collections.singletonList("ROLE_TRYER"))
                        .build()
        );
        userRepository.save(defaultUser);
        User testUser = userRepository.findByEmail("tester@90s.com").orElse(
                User.builder()
                        .email("tester@90s.com")
                        .name("90s_tester")
                        .password(passwordEncoder.encode("test"))
                        .phone("010-0000-0000")
                        .roles(Collections.singletonList("ROLE_TESTER"))
                        .build()
        );
        userRepository.save(testUser);


        System.out.println("JWT TOKEN : " + jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles()));


    }
}
