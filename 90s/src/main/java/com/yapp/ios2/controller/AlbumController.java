package com.yapp.ios2.controller;

import com.yapp.ios2.service.IAlbumService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Album createAlbum(@RequestBody Map<String, String> json){

        Integer photoLimit = Integer.parseInt(json.get("photoLimit"));
        Long userUid = Long.parseLong(json.get("user"));
        String name = json.get("name");
        Long layoutUid = Long.parseLong(json.get("layoutUid"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate endDate = LocalDate.parse(json.get("endDate"), formatter);

        return albumService.create(name, photoLimit, userUid, layoutUid, endDate);
    }

    @PostMapping("/album/addUser")
    public Map<String, Object> addUser(@RequestBody Map<String, Optional> json){
        Map<String, Object> result = new HashMap<>();
        AlbumOwner albumOwner = albumService.addOwner((Long)json.get("album").get(), (Long)json.get("user").get(), (String)json.get("role").get());
        result.put("albumOwner", albumOwner);
        return result;
    }

    @PostMapping("/album/get")
    public List<Album> getAlbums(@RequestBody Map<String, String> json){
        List<Album> albums = albumService.getAlbums(Long.parseLong(json.get("user")));
        return albums;
    }

}
