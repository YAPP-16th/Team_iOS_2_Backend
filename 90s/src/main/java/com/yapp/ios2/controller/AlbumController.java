package com.yapp.ios2.controller;

import com.amazonaws.util.IOUtils;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.AlbumOwnerDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags = {"2. Album"})
@RestController
@RequestMapping("/album/*")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

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
                albumInfo.getCoverUid(),
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
    @ResponseBody
    public List<Album> getAlbums(@AuthenticationPrincipal UserDetails user){
        List<Album> albums = albumService.getAlbumsByUser(userService.getUserByEmail(user.getUsername()));

        albums.forEach(
                album -> {
                    albumService.completeChecker(album.getUid());
                }
        );

        return albums;
    }

    @PostMapping("/getAlbum")
    public Album getAlbum(@RequestBody AlbumDto.AlbumUid albumUid){
        Album album = albumService.getAlbum(albumUid.getUid());
        albumService.completeChecker(album.getUid());
        return album;
    }


    @GetMapping("/getAlbumPassword/{albumUid}")
    public String getAlbumPassword(@PathVariable("albumUid") Long albumUid){

        Album album = albumService.getAlbum(albumUid);

        return album.getPassword().toString();

    }

    @PostMapping("/joinAlbumByPassword")
    public AlbumOwner joinAlbumByPassword(@AuthenticationPrincipal User user, @RequestBody AlbumDto.AlbumPassword albumPassword){
        return albumService.joinAlbumByPassword(albumPassword.getPassword(), user);
    }

    @GetMapping("/updateAlbumPassword/{albumUid}")
    public String updateAlbumPassword(@PathVariable("albumUid") Long albumUid){

        Album album = albumService.getAlbum(albumUid);

        album.setPassword(UUID.randomUUID());

        albumRepository.save(album);

        return album.getPassword().toString();

    }

    @PostMapping(value = "/getAlbumCover", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> getAlbumCover(@RequestBody AlbumDto.AlbumUid albumUid,
                                            HttpServletResponse response) throws IOException {
        Album album =albumService.getAlbum(albumUid.getUid());

        ClassPathResource resource = new ClassPathResource(
                album.getCover().getPath()
        );

        byte[] bytes = IOUtils.toByteArray(resource.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + album.getName() + "_cover.jpeg");

        return new HttpEntity(bytes, headers);
    }

    @PostMapping("/createAlbumOrder")
    public AlbumOrder createAlbumOrder(@RequestBody AlbumDto.AlbumOrderInfo albumOrderInfo){

        AlbumOrder newAlbumOrder = albumService.createAlbumOrder(albumOrderInfo);

        return newAlbumOrder;

    }

    @PostMapping("/changeAlbumOrderStatus")
    public void changeAlbumOrderStatus() {
    }

    @PostMapping("/getAlbumOwners")
    public List<AlbumOwnerDto.AlbumOwnerInfo> getAlbumOwners(@AuthenticationPrincipal User user, @RequestBody AlbumDto.AlbumUid albumUid){
        List<AlbumOwnerDto.AlbumOwnerInfo> albumOwners = albumService.getAlbumOwners(albumUid.getUid());

        for(AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo : albumOwners){
            if(albumOwnerInfo.getUserUid().intValue() != user.getUid().intValue()){
                albumOwnerInfo.setUserUid(null);
            }
        }

        return albumOwners;
    }

    @GetMapping("/plusCount/{albumUid}")
    @Secured({"TESTER", "USER"})
    public void plusCount(@PathVariable("albumUid") Long albumUid){

        albumService.plusCount(albumUid);

    }

}
