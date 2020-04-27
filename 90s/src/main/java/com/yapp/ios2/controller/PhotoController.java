package com.yapp.ios2.controller;

import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @GetMapping("/photo")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetails user){

        return "WELCOME, " + user.getUsername() +
                " HERE IS PHOTO HOME " +
                user.getAuthorities();
    }


    @PostMapping(value = "/photo/upload")
    @ResponseBody
    public String upload(@RequestParam(value="image") MultipartFile image, @RequestParam("albumUid") Long albumUid, @RequestParam("photoOrder") Integer photoOrder, @AuthenticationPrincipal UserDetails user) throws IOException {
        String url = photoService.upload(image, albumUid, photoOrder, userService.getUserByEmail(user.getUsername()).getUid());

        return url;
    }

    @PostMapping(value = "/photo/download")
    @ResponseBody
    public byte[] download(@RequestBody PhotoDto photoInfo) throws IOException {

        byte[] file = photoService.download(photoInfo.getAlbumUid(), photoInfo.getPhotoUid());

        return file;
    }

}
