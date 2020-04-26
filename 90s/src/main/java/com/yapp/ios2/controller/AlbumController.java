package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.AlbumOwnerDto;
import com.yapp.ios2.dto.BooleanResultDto;
import com.yapp.ios2.dto.UserUidDto;
import com.yapp.ios2.service.IAlbumService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Album"})
@RestController
@RequestMapping("/album/*")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @GetMapping("/")
    public String home(){
        System.out.println("album home");
        return "Welcome, HERE IS ALBUM HOME";
    }

    @PostMapping("/create")
    @ResponseBody
    public Album createAlbum(@RequestBody AlbumDto albumInfo){

        Album newAlbum = albumService.create(
                albumInfo.getName(),
                albumInfo.getPhotoLimit(),
                albumInfo.getUserUid(),
                albumInfo.getLayoutUid(),
                albumInfo.getEndDate()
        );

        return newAlbum;
    }

    @PostMapping("/addUser")
    public BooleanResultDto addUser(@RequestBody AlbumOwnerDto albumOwnerInfo){
        AlbumOwner albumOwner = albumService.addOwner(
                albumOwnerInfo.getAlbumUid(),
                albumOwnerInfo.getUserUid(),
                albumOwnerInfo.getRole()
        );
        BooleanResultDto result = new BooleanResultDto();
        if(albumOwner != null){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @PostMapping("/get")
    public List<Album> getAlbums(@RequestBody UserUidDto user){
        List<Album> albums = albumService.getAlbums(user.getUserUid());
        return albums;
    }

}
