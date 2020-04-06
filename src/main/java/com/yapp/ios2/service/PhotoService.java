package com.yapp.ios2.service;

import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.vo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService implements IPhotoService {

    @Autowired
    private IS3Service s3Service;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public String upload(MultipartFile photo, Integer albumUid, Integer photoOrder, Integer uploader) throws IOException{
        String fileName = albumUid.toString() + "/" + photoOrder.toString();
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
}
