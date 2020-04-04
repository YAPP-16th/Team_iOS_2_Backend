package com.yapp.ios2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

//    @Autowired
//    private IAlbumService albumService;
//
//    @GetMapping("/album")
//    public String home(){
//        System.out.println("album home");
//        return "Welcome, HERE IS ALBUM HOME";
//    }
//
//    @PostMapping("/album/create")
//    @ResponseBody
//    public Album createAlbum(@RequestBody Map<String, String> album){
//
////        System.out.println(album.getName());
//
//        return albumService.create(album.get("name"), Integer.parseInt(album.get("photoLimit")));
//    }

    @Autowired
    private UserService userService;


}
