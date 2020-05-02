package com.yapp.ios2.service;

import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService{

    @Autowired
    private S3Service s3Service;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    public String upload(MultipartFile photo, Long albumUid, Integer photoOrder, Long uploader) throws IOException{

        String fileName = albumUid.toString() + "/" + photoOrder + ".jpeg";
        String url = s3Service.upload(photo, fileName);

        Photo newPhoto = Photo.builder()
                .album(albumRepository.findById(albumUid).get())
                .photoOrder(photoOrder)
                .uploader(userRepository.findById(uploader).get())
                .url(url)
                .build();


        photoRepository.save(newPhoto);

        return url;
    }

    public List<Photo> upload(MultipartFile[] photos, Long albumUid, Long uploader) throws IOException{

        List<Photo> photoList = new ArrayList<>();

        Integer lastPhotoOrder = photoRepository.findFirstByAlbumOrderByPhotoOrderAsc(
                albumRepository.findById(albumUid).get())
                .orElse(Photo.builder().photoOrder(0).build())
                .getPhotoOrder();

        Album album = albumRepository.findById(albumUid).get();
        User user = userRepository.findById(uploader).get();

        System.out.println("lastPhotoOrder");
        System.out.println(lastPhotoOrder);
        System.out.println("lastPhotoOrder");

        for(MultipartFile photo : photos){
            Photo newPhoto = Photo.builder()
                    .album(album)
                    .photoOrder(lastPhotoOrder++)
                    .uploader(user)
                    .build();

            photoRepository.save(newPhoto);

            String fileName = albumUid.toString() + "/" + newPhoto.getUid() + ".jpeg";
            String url = s3Service.upload(photo, fileName);
            newPhoto.setUrl(url);
            photoRepository.save(newPhoto);

            photoList.add(newPhoto);
        }


        return photoList;
    }

    public byte[] download(Long album, Long photo) throws IOException {

        byte[] file = s3Service.download(album, photo.toString());

        return file;
    }
}
