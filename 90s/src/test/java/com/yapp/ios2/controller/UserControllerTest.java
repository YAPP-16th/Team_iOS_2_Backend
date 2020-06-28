package com.yapp.ios2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.TestConfig;
import com.yapp.ios2.config.JwtFilter;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.JoinDto;
import com.yapp.ios2.dto.LoginDto;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends TestInit{
    @Test
    public void 회원가입() throws Exception {

        JoinDto joinDto = new JoinDto();
        joinDto.setEmail("test9999@90s.com");
        joinDto.setName("test");
        joinDto.setPassword("test");
        joinDto.setPhone("010-9523-3114");
        joinDto.setSosial(false);

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(joinDto);

        mockMvc.perform(
                post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE1OTMyMzI0MzIsImV4cCI6MjIyMzk1MjQzMn0.T6xSOI3n0NtHgK6abKt3A_aTcKTF5mT563pq6wBV4Nw")
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

    @Test
    public void 로그인() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("tester0@90s.com");
        loginDto.setPassword("test");
        loginDto.setSosial(false);


        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("email").description("이메일").attributes(new Attributes.Attribute("format","test@90s.com")),
                                fieldWithPath("password").description("비밀번호").attributes(new Attributes.Attribute("format","카카오 로그인 시에는 null로 보내지 않아도 무관합니다.")),
                                fieldWithPath("sosial").type("Boolean").description("카카오 로그인 여부").attributes(new Attributes.Attribute("format","true / false"))
                        )
                ));
    }

    @Test
    public void 회원탈퇴() throws Exception {

        createTester();

        mockMvc.perform(
                get("/user/signout")
                        .header("X-AUTH-TOKEN", jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());
    }
}