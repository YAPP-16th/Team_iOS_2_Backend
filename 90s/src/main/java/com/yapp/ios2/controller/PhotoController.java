package com.yapp.ios2.controller;

import com.yapp.ios2.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private IPhotoService photoService;

    @GetMapping("/photo")
    @ResponseBody
    public String home(){
        return "WELCOME, HERE IS PHOTO HOME";
    }


    @PostMapping(value = "/photo/upload")
    @ResponseBody
    public String upload(@RequestParam(value="image") MultipartFile image, @RequestParam("albumUid") Integer albumUid, @RequestParam("photoOrder") Integer photoOrder, @RequestParam("uploader") Integer uploader) throws IOException {
        System.out.println("upload processing");
        System.out.println(image.getName());
        System.out.println(image.getOriginalFilename());
        String url = photoService.upload(image, albumUid, photoOrder, uploader);
        return url;
//        return "YAHO";
    }

}
