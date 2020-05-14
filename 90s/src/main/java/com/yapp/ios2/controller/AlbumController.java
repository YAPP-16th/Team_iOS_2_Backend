package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.AlbumOwnerDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import com.yapp.ios2.vo.AlbumOwner;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Album"})
@RestController
@RequestMapping("/album/*")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        System.out.println("album home");
        return "Welcome, HERE IS ALBUM HOME";
    }

    @PostMapping("/create")
    @ResponseBody
    public Album createAlbum(@AuthenticationPrincipal UserDetails user, @RequestBody AlbumDto.AlbumInfo albumInfo){

        Album newAlbum = albumService.create(
                albumInfo.getName(),
                albumInfo.getPhotoLimit(),
                userService.getUserByEmail(user.getUsername()).getUid(),
                albumInfo.getLayoutUid(),
                albumInfo.getEndDate()
        );

        return newAlbum;
    }

    @PostMapping("/addUser")
    public ResponseDto.BooleanDto addUser(@RequestBody AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo){
        AlbumOwner albumOwner = albumService.addOwner(albumOwnerInfo.getAlbumUid(), albumOwnerInfo.getUserUid(), albumOwnerInfo.getRole());
        ResponseDto.BooleanDto result = new ResponseDto.BooleanDto();
        if(albumOwner != null){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @PostMapping("/removeUser")
    public ResponseDto.BooleanDto removeUser(@RequestBody AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo){
        albumService.removeOwner(
                albumOwnerInfo.getAlbumUid(),
                albumOwnerInfo.getUserUid()
        );
        ResponseDto.BooleanDto result = new ResponseDto.BooleanDto();
        result.setResult(true);

        return result;
    }



    @GetMapping("/getAlbums")
    public List<Album> getAlbums(@AuthenticationPrincipal UserDetails user){
        List<Album> albums = albumService.getAlbumsByUser(userService.getUserByEmail(user.getUsername()));
        return albums;
    }

    @PostMapping("/getAlbum")
    public Album getAlbum(AlbumDto.AlbumUid albumUid){
        Album album = albumService.getAlbum(albumUid.getUid());
        return album;
    }

    @PostMapping("/createAlbumOrder")
    public AlbumOrder createAlbumOrder(AlbumDto.AlbumOrderInfo albumOrderInfo){

        AlbumOrder newAlbumOrder = albumService.createAlbumOrder(albumOrderInfo);

        return newAlbumOrder;

    }

    @PostMapping("/changeAlbumOrderStatus")
    public void changeAlbumOrderStatus() {
    }

    @PostMapping("/getAlbumOwners")
    public List<AlbumOwnerDto.AlbumOwnerInfo> getAlbumOwners(@RequestBody AlbumDto.AlbumUid albumUid){
        List<AlbumOwnerDto.AlbumOwnerInfo> albumOwners = albumService.getAlbumOwners(albumUid.getUid());
        return albumOwners;
    }

    @GetMapping("/plusCount")
    public void plusCount(@RequestBody AlbumDto.AlbumUid albumUid){

        albumService.plusCount(albumUid.getUid());

    }

}
