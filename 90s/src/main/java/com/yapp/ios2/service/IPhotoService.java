package com.yapp.ios2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPhotoService{

    String upload(MultipartFile photo, Integer albumUid, Integer photoOrder, Integer uploader) throws IOException;

    byte[] download(Long album, Long photo) throws IOException;

}
