package com.yapp.ios2.controller;

import com.amazonaws.services.mq.model.NotFoundException;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Photo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Api(tags = {"3. Photo"})
@RestController
@RequestMapping("/photo/*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoRepository photoRepository;


    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetails user){

        return "WELCOME, " + user.getUsername() +
                " HERE IS PHOTO HOME " +
                user.getAuthorities();
    }


    @PostMapping(value = "/upload")
    @ResponseBody
    public List<Photo> upload(@RequestParam(value="image") MultipartFile[] images, @RequestParam("albumUid") Long albumUid, @AuthenticationPrincipal UserDetails user) throws IOException {

        List<Photo> photos = photoService.upload(images, albumUid);

        return photos;
    }

    @PostMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] download(@RequestBody PhotoDto.PhotoInfo photoInfo) throws IOException {
        byte[] bytes = photoService.download(photoInfo.getAlbumUid(), photoInfo.getPhotoUid());

        return bytes;
    }

    @GetMapping(value = "/download/{albumUid}/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> download(@PathVariable("albumUid") Long albumUid,
                                       @PathVariable("photoUid") Long photoUid,
                                       HttpServletResponse response) throws IOException {
        byte[] bytes = photoService.download(albumUid, photoUid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + photoUid.toString() + ".jpeg");

        return new HttpEntity(bytes, headers);
    }

    @PostMapping(value = "/getPhotos")
    @ResponseBody
    public List<Photo> getPhotos(@RequestBody AlbumDto.AlbumUid albumUid){

        List<Photo> photos = photoService.getPhotos(albumUid.getUid());

        return photos;
    }
//    public List<PhotoDto.PhotoInfo> getPhotos(@RequestBody AlbumDto.AlbumUid albumUid){
//
//        List<PhotoDto.PhotoInfo> photos = PhotoDto.convertFromPhotoListToPhotoInfoList(
//                photoService.getPhotos(albumUid.getUid())
//        );
//
//        return photos;
//    }

    @PutMapping("/updatePhotoOrder/{photoUid}/{photoOrder}")
    @ResponseBody
    public Photo updatePhotoOrder(@PathVariable("photoUid") Long photoUid, @PathVariable("photoOrder") Integer photoOrder){
        Photo photo = photoRepository.findById(photoUid).orElseThrow(
                () -> new NotFoundException("없는 사진인데!")
        );

        photo.setPhotoOrder(photoOrder);

        photoRepository.save(photo);

        return photo;
    }

}
