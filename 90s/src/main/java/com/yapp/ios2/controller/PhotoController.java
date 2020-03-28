package com.yapp.ios2.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class PhotoController {

    @GetMapping("/upload-photo")
    public String home(){
        return "A";
    }
}
