package com.yapp.ios2.controller;

import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Api(tags = {"3. Photo"})
@RestController
@RequestMapping("/photo/*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetails user){

        return "WELCOME, " + user.getUsername() +
                " HERE IS PHOTO HOME " +
                user.getAuthorities();
    }


    @PostMapping(value = "/upload")
    @ResponseBody
    public List<ResponseDto.UrlDto> upload(@RequestParam(value="images") MultipartFile[] images, @RequestParam("albumUid") Long albumUid, @RequestParam("photoOrder") Integer photoOrder, @AuthenticationPrincipal UserDetails user) throws IOException {

        List<ResponseDto.UrlDto> urlDtos = new ArrayList();

        for(MultipartFile image : images){
            ResponseDto.UrlDto urlDto = new ResponseDto.UrlDto();
            String url = photoService.upload(image, albumUid, photoOrder, userService.getUserByEmail(user.getUsername()).getUid());
            urlDto.setUrl(url);
            urlDtos.add(urlDto);
        }


        return urlDtos;
    }

    @PostMapping(value = "/download")
    @ResponseBody
    public byte[] download(@RequestBody PhotoDto.PhotoInfoDto photoInfo) throws IOException {
//    public ResponseEntity<Resource> download(@RequestBody PhotoDto.PhotoInfoDto photoInfo) throws IOException {

        byte[] bytes = photoService.download(photoInfo.getAlbumUid(), photoInfo.getPhotoUid());

        return bytes;
//        ByteArrayResource resource = new ByteArrayResource(bytes);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }

}
