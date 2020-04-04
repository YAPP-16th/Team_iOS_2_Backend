package com.yapp.ios2.controller;

import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.service.IAlbumService;
import com.yapp.ios2.vo.Album;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @GetMapping("/album")
    public String home(){
        System.out.println("album home");
        return "Welcome, HERE IS ALBUM HOME";
    }

    @PostMapping("/album/create")
    @ResponseBody
    public Album createAlbum(@RequestBody Map<String, String> album){

//        System.out.println(album.getName());

        return albumService.create(album.get("name"), Integer.parseInt(album.get("photoLimit")));
    }
}
