package com.yapp.ios2.service;

import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.vo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService{

    @Autowired
    private S3Service s3Service;

    @Autowired
    private PhotoRepository photoRepository;

    public String upload(MultipartFile photo, Long albumUid, Integer photoOrder, Long uploader) throws IOException{

        String fileName = albumUid.toString() + "/" + photoOrder + ".jpeg";
        String url = s3Service.upload(photo, fileName);

        Photo newPhoto = Photo.builder()
                .albumUid(albumUid)
                .photoOrder(photoOrder)
                .uploader(uploader)
                .url(url)
                .build();


        photoRepository.save(newPhoto);

        return url;
    }

    public byte[] download(Long album, Long photo) throws IOException {

        byte[] file = s3Service.download(album, photo.toString());

        return file;
    }
}
