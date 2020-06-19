package com.yapp.ios2.config;

import com.amazonaws.util.IOUtils;
import com.google.common.collect.Lists;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.CoverRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor

@Order(3)
@Component
@Lazy
public class DefaultUserInitializer implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    CoverRepository coverRepository;
    @Autowired
    AlbumService albumService;
    @Autowired
    PhotoService photoService;
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
//        DefaultUser Album&Photo Init
        if(albumRepository.findByUser(defaultUser).isEmpty()){
            List<String> names = Arrays.asList(
                    "소듕한 내 일상들",
                    "집사라서 행복해",
                    "금강산도식후경",
                    "내 이야기",
                    "함께한 지 365일",
                    "우리가 계절을 기억하는 법");
            List<Integer> photos = Arrays.asList(
                    5,
                    6,
                    6,
                    6,
                    5,
                    6
            );
            List<Long> covers = Arrays.asList(
                    1L,
                    2L,
                    3L,
                    4L,
                    6L,
                    7L
            );
            Integer photoCnt = 1;
            for(int i = 0; i < 6; i++){
                Album newAlbum = albumService.create(
                        names.get(i),
                        photos.get(i),
                        defaultUser.getUid(),
                        covers.get(i),
                        covers.get(i),
                        LocalDate.now()
                );

                for(int j = 0; j < photos.get(i); j++){
                    ClassPathResource resource = new ClassPathResource(
                            "static/" + String.valueOf(i+1) + "/" + String.valueOf(j+1) + ".png"
                    );
                    InputStream inputStream = resource.getInputStream();

                    MultipartFile multipartFile = new MockMultipartFile("file",
                            String.valueOf(j+1) + ".png", "text/plain", IOUtils.toByteArray(inputStream));
                    MultipartFile[] multipartFiles = {multipartFile};
                    photoService.upload(multipartFiles,newAlbum.getUid());
                }
            }
        }

//        1. 앨범 제목: 소듕한 내 일상들
//        파일명: polaroid
//        앨범커버이미지: albumcover1990Copy
//
//        2. 앨범 제목: 집사라서 행복해
//        파일명: memory
//        앨범커버이미지: albumcoverParadiso
//
//        3. 앨범 제목: 금강산도식후경
//        파일명: portraw
//        앨범커버이미지: albumcoverHappilyeverafter
//
//        4. 앨범 제목: 내 이야기
//        파일명: portrab
//        앨범커버이미지: albumcoverFavoritethings
//
//        5. 앨범 제목: 함께한 지 365일
//        파일명: mini
//        앨범커버이미지: albumcoverLessbutbetter
//
//        6. 앨범 제목: 우리가 계절을 기억하는 법
//        파일명: filmroll
//        앨범커버이미지: albumcover90Sretroclub

        for(int i = 0; i < 31; i++){
            String name = "tester" + i;

            if(userRepository.findByEmail(name + "@90s.com").isPresent()) continue;

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
