package com.yapp.ios2.controller;

import com.yapp.ios2.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("dev")
public class TestInit {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    MockMvc mockMvc;
    RestDocumentationResultHandler document;

    User testUser;

    String jwt;

    @Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)
                        .uris().withScheme("https").withHost("90s.com").withPort(443))
                .apply(springSecurity())
                .alwaysDo(document)
                .build();

    }

    void createTester(){
        this.testUser = userRepository.findByEmail("tester0000@90s.com").orElse(
                User.builder()
                        .email("tester0000@90s.com")
                        .name("90s_tester")
                        .password(passwordEncoder.encode("test"))
                        .phone("010-0000-0000")
                        .roles(Collections.singletonList("ROLE_TESTER"))
                        .build()
        );
        this.jwt = jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles());
    }
    void deleteTester(){
        userRepository.delete(this.testUser);
    }

//    @After
//    public void After(){
//
//        userRepository.delete(this.testUser);
//
//    }


}
