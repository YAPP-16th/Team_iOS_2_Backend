package com.yapp.ios2.config;

import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor

@Configuration
public class defaultUserConfig implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    AlbumService albumService;

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

        for(int i = 0; i < 31; i++){
            String name = "tester" + i;

            if(userRepository.findByEmail(name + "@90s.com").isPresent()) break;

            User testUser = userRepository.findByEmail(name + "@90s.com").orElse(
                    User.builder()
                            .email(name + "@90s.com")
                            .name("90s_" + name)
                            .password(passwordEncoder.encode("test"))
                            .phone("010-0000-0000")
                            .roles(Collections.singletonList("ROLE_TESTER"))
                            .build()
            );
            userRepository.save(testUser);
            System.out.println(name + " JWT TOKEN : " + jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles()));
            for(int j = 0; j < 6; j++){
                albumService.create(
                        "Album" + String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)),
                        7,
                        testUser.getUid(),
                        0L,
                        Long.valueOf(j+1),
                        LocalDate.now().plusDays(100)
                );
            }
        }








    }
}
