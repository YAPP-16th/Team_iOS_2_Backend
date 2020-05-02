package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
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
    public Album createAlbum(@AuthenticationPrincipal UserDetails user, @RequestBody AlbumDto.AlbumInfoDto albumInfo){

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
    public ResponseDto.BooleanDto addUser(@RequestBody AlbumDto.AlbumOwnerDto albumOwnerInfo){
        AlbumOwner albumOwner = albumService.addOwner(
                albumOwnerInfo.getAlbumUid(),
                albumOwnerInfo.getUserUid(),
                albumOwnerInfo.getRole()
        );
        ResponseDto.BooleanDto result = new ResponseDto.BooleanDto();
        if(albumOwner != null){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @GetMapping("/getAlbums")
    public List<Album> getAlbums(@AuthenticationPrincipal UserDetails user){
        List<Album> albums = albumService.getAlbums(userService.getUserByEmail(user.getUsername()).getUid());
        return albums;
    }

    @PostMapping("/getAlbum")
    public Album getAlbum(AlbumDto.AlbumUidDto albumUidDto){
        Album album = albumService.getAlbum(albumUidDto.getUid());
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
    public List<User> getAlbumOwners(AlbumDto.AlbumUidDto albumUidDto){
        List<User> albumOwners = albumService.getAlbumOwners(albumUidDto.getUid());
        return albumOwners;
    }

    @GetMapping("/plusCount")
    public void plusCount(AlbumDto.AlbumUidDto albumUidDto){

        albumService.plusCount(albumUidDto.getUid());

    }

}
