package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Photo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<Photo> upload(@RequestParam(value="image") MultipartFile[] images, @RequestParam("albumUid") Long albumUid, @AuthenticationPrincipal UserDetails user) throws IOException {

        List<Photo> photos = photoService.upload(images, albumUid, userService.getUserByEmail(user.getUsername()).getUid());

        return photos;
    }

    @PostMapping(value = "/download")
    @ResponseBody
    public byte[] download(@RequestBody PhotoDto.PhotoInfo photoInfo) throws IOException {
        byte[] bytes = photoService.download(photoInfo.getAlbumUid(), photoInfo.getPhotoUid());

        return bytes;
    }

    @PostMapping(value = "/getPhotos")
    @ResponseBody
    public List<PhotoDto.PhotoInfo> getPhotos(@RequestBody AlbumDto.AlbumUid albumUid){

        List<PhotoDto.PhotoInfo> photos = PhotoDto.convertFromPhotoListToPhotoInfoList(
                photoService.getPhotos(albumUid.getUid())
        );

        return photos;
    }

}
