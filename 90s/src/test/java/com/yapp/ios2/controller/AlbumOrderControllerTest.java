package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.LoginDto;
import com.yapp.ios2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("dev")
public class AlbumOrderControllerTest {

    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document)
                .build();
    }

    @Test
    public void 주문생성() throws Exception {

        AlbumDto.AlbumOrderInfo albumOrderInfo = new AlbumDto.AlbumOrderInfo();

        albumOrderInfo.setAddress("서울특별시 종로구 행복로 277");
        albumOrderInfo.setAddressDetail("행복아파트 101동 1303호");
        albumOrderInfo.setCost("5000");
        albumOrderInfo.setMessage("경비실에 맡겨주세요!");
        albumOrderInfo.setPaperType1(1L);
        albumOrderInfo.setPaperType2(1L);
        albumOrderInfo.setPostType(1L);
        albumOrderInfo.setPhoneNum("010-0000-0000");
        albumOrderInfo.setPostalCode("123456");
        albumOrderInfo.setRecipient("이우경");


        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(albumOrderInfo);

        mockMvc.perform(
                post("/album/order/createAlbumOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("address").description("주").attributes(new Attributes.Attribute("format","서울특별시 종로구 행복로 11")),
                                fieldWithPath("addressDetail").description("상세주").attributes(new Attributes.Attribute("format","행복아파트 101동 1303호")),
                                fieldWithPath("cost").description("가격").attributes(new Attributes.Attribute("format","15000")),
                                fieldWithPath("message").description("배송 요청 메세지").attributes(new Attributes.Attribute("format","경비실에 맡겨주세요!")),
                                fieldWithPath("paperType1").description("종이 종류 1 - 무광,유광").attributes(new Attributes.Attribute("format","1 : 유광, 2 : 무광")),
                                fieldWithPath("paperType2").description("종이 종류 2 - 일반, 고급, 최고급").attributes(new Attributes.Attribute("format","1 : 일반, 2 : 고급, 3 : 최고급")),
                                fieldWithPath("postType").description("배송 종류").attributes(new Attributes.Attribute("format","1 : 일반, 2 : 특급배송, 3 : 등기")),
                                fieldWithPath("phoneNum").description("핸드폰 번호").attributes(new Attributes.Attribute("format","")),
                                fieldWithPath("postalCode").description("우편 번호").attributes(new Attributes.Attribute("format","")),
                                fieldWithPath("recipient").description("수취인").attributes(new Attributes.Attribute("format","")),
                                fieldWithPath("trackingNum").description("송장번호").attributes(new Attributes.Attribute("format","")).optional()
                        )
                ));
    }

}
