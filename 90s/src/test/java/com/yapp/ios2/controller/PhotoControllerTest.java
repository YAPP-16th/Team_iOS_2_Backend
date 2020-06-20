//package com.yapp.ios2.controller;
//
//import com.yapp.ios2.config.JwtProvider;
//import com.yapp.ios2.repository.PhotoRepository;
//import com.yapp.ios2.service.AlbumService;
//import com.yapp.ios2.service.PhotoService;
//import com.yapp.ios2.service.S3Service;
//import com.yapp.ios2.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@WebMvcTest(PhotoController.class)
//public class PhotoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    PhotoRepository photoRepository;
//    @MockBean
//    PhotoService photoService;
//    @MockBean
//    S3Service s3Service;
//    @MockBean
//    PhotoController photoController;
//    @MockBean
//    AlbumService albumService;
//    @MockBean
//    UserService userService;
//    @MockBean
//    JwtProvider jwtProvider;
//
//
////    @MockBean
////    PhotoService photoService;
//
//    private final String URL = "/employee/";
//
//    @Test
//    public void Home() throws Exception {
//
//        // execute
//        MvcResult result = mockMvc.perform(
//                MockMvcRequestBuilders.get("/photo/")).andDo(print()).andReturn();
//
//    }
//}
