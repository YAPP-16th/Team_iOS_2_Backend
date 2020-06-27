package com.yapp.ios2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.dto.JoinDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
public class UserControllerTest2{

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RestDocumentationResultHandler document;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
//                .apply(springSecurity())
                .build();
    }

    @Test
    public void join() throws Exception {

        JoinDto joinDto = new JoinDto();
        joinDto.setEmail("test909@90s.com");
        joinDto.setName("test");
        joinDto.setPassword("test");
        joinDto.setPhone("010-9523-3114");
        joinDto.setSosial(false);

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(joinDto);

        mockMvc.perform(
                post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("email").description("이메일").attributes(new Attributes.Attribute("format", "test@90s.com")),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("password").description("비밀번호").attributes(new Attributes.Attribute("format", "카카오 로그인 시에는 null로 보내지 않아도 무관합니다.")),
                                fieldWithPath("phone").type("String").description("핸드폰 번호").attributes(new Attributes.Attribute("format", "010-1234-5678")),
                                fieldWithPath("sosial").type("Boolean").description("카카오 로그인 여부").attributes(new Attributes.Attribute("format", "true / false"))
                        )
                ));
    }

//    @Test
//    public void login() throws Exception {
//
//        LoginDto loginDto = new LoginDto();
//        loginDto.setEmail("tester0@90s.com");
//        loginDto.setPassword("test");
//        loginDto.setSosial(false);
//
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);
//
//        mockMvc.perform(
//                post("/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestFields(
//                                fieldWithPath("email").description("이메일").attributes(new Attributes.Attribute("format","test@90s.com")),
//                                fieldWithPath("password").description("비밀번호").attributes(new Attributes.Attribute("format","카카오 로그인 시에는 null로 보내지 않아도 무관합니다.")),
//                                fieldWithPath("sosial").type("Boolean").description("카카오 로그인 여부").attributes(new Attributes.Attribute("format","true / false"))
//                        )
//                ));
//    }
//
//    @Test
//    public void 회원탈퇴() throws Exception {
//        System.out.println("111111111");
//        User user = userService.getUserByEmail("tester19@90s.com");
//        System.out.println("222222222");
//        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
//        System.out.println("333333333");
//
////        ObjectMapper json = new ObjectMapper();
////        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);
//
//        mockMvc.perform(
//                get("/user/signout")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    

}