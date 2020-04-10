package com.yapp.ios2.controller;

import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.service.IAlbumService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "album",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{'property': 'test'}", mediaType = "application/json")
                            }))
    })
    @ResponseBody
    public Album createAlbum(@RequestBody Map<String, String> json){

        Integer photoLimit = Integer.parseInt(json.get("photoLimit"));
        Long userUid = Long.parseLong(json.get("user"));
        String name = json.get("name");
        Long layoutUid = Long.parseLong(json.get("layoutUid"));
        return albumService.create(name, photoLimit, userUid, layoutUid);
    }

    @PostMapping("/album/get")
    public List<Album> getAlbums(@RequestBody Map<String, String> json){
        List<Album> albums = albumService.getAlbums(Long.parseLong(json.get("user")));
        return albums;
    }

}
